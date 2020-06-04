package in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.payment;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.modules.bmw.helper.payment.PaymentService;
import in.hocg.eagle.modules.bmw.helper.payment.dto.PaymentWay;
import in.hocg.payment.convert.StringConvert;
import in.hocg.payment.resolve.StringResolve;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.message.UnifiedOrderMessage;

/**
 * Created by hocgin on 2019/12/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class WxPayPaymentMessageRule extends StringResolve.StringRule<UnifiedOrderMessage, UnifiedOrderMessage.Result> {
    public WxPayPaymentMessageRule(WxPayService payService) {
        super(new StringConvert<UnifiedOrderMessage>() {
            @Override
            public <R extends UnifiedOrderMessage> R convert(String body, Class<R> clazz) {
                return payService.message(body, clazz);
            }
        }, WxPayPaymentMessageRule::handleMessage);
    }

    protected static UnifiedOrderMessage.Result handleMessage(UnifiedOrderMessage message) {
        try {
            final String outTradeNo = message.getOutTradeNo();
            final String tradeNo = message.getTransactionId();
            SpringContext.getBean(PaymentService.class).handlePaymentCallback(PaymentWay.WxPayWithJSAPI, outTradeNo, tradeNo, true);
            return UnifiedOrderMessage.Result.builder().returnMsg("SUCCESS").returnCode("OK").build();
        } catch (Exception e) {
            return UnifiedOrderMessage.Result.builder().returnMsg("FAIL").returnCode("FAIL").build();
        }
    }
}
