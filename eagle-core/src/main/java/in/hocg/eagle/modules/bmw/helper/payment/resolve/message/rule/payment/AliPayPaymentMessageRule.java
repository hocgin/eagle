package in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.payment;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.modules.bmw.helper.payment.PaymentService;
import in.hocg.eagle.modules.bmw.helper.payment.dto.PaymentWay;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.message.TradeStatusSyncMessage;
import in.hocg.payment.convert.StringConvert;
import in.hocg.payment.resolve.StringResolve;

/**
 * Created by hocgin on 2019/12/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class AliPayPaymentMessageRule extends StringResolve.StringRule<TradeStatusSyncMessage, TradeStatusSyncMessage.Result> {
    public AliPayPaymentMessageRule(AliPayService payService) {
        super(new StringConvert<TradeStatusSyncMessage>() {
            @Override
            public <R extends TradeStatusSyncMessage> R convert(String body, Class<R> clazz) {
                return payService.message(body, clazz);
            }
        }, AliPayPaymentMessageRule::handleMessage);

    }

    protected static TradeStatusSyncMessage.Result handleMessage(TradeStatusSyncMessage message) {
        try {
            final String outTradeNo = message.getOutTradeNo();
            final String tradeNo = message.getTradeNo();
            SpringContext.getBean(PaymentService.class).handlePaymentCallback(PaymentWay.AliPay, outTradeNo, tradeNo, true);
            return TradeStatusSyncMessage.Result.builder().result("success").build();
        } catch (Exception e) {
            return TradeStatusSyncMessage.Result.builder().result("fail").build();
        }
    }
}
