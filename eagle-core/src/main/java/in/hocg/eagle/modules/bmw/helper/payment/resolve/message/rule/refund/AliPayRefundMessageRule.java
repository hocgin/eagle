package in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.refund;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.modules.bmw.helper.payment.PaymentService;
import in.hocg.eagle.modules.bmw.helper.payment.dto.PaymentWay;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.message.TradeStatusSyncMessage;
import in.hocg.payment.convert.StringConvert;
import in.hocg.payment.resolve.StringResolve;
import in.hocg.payment.wxpay.v2.message.PayRefundMessage;

/**
 * Created by hocgin on 2019/12/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class AliPayRefundMessageRule extends StringResolve.StringRule<TradeStatusSyncMessage, TradeStatusSyncMessage.Result> {
    public AliPayRefundMessageRule(AliPayService payService) {
        super(new StringConvert<PayRefundMessage>() {
            @Override
            public <R extends PayRefundMessage> R convert(String body, Class<R> clazz) {
                return payService.message(body, clazz);
            }
        }, AliPayRefundMessageRule::handleMessage);
    }

    protected static TradeStatusSyncMessage.Result handleMessage(TradeStatusSyncMessage message) {
        try {
            final String refundTradeNo = message.getTradeNo();
            final String refundSn = message.getOutTradeNo();
            SpringContext.getBean(PaymentService.class).handleRefundCallback(PaymentWay.AliPayWithApp, refundSn, refundTradeNo, true);
            return TradeStatusSyncMessage.Result.builder().result("success").build();
        } catch (Exception e) {
            return TradeStatusSyncMessage.Result.builder().result("fail").build();
        }
    }
}
