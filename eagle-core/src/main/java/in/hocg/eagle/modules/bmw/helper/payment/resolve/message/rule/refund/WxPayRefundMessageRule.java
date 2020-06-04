package in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.refund;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.modules.bmw.helper.payment.PaymentService;
import in.hocg.eagle.modules.bmw.helper.payment.dto.PaymentWay;
import in.hocg.payment.convert.StringConvert;
import in.hocg.payment.resolve.StringResolve;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.message.PayRefundMessage;

/**
 * Created by hocgin on 2019/12/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class WxPayRefundMessageRule extends StringResolve.StringRule<PayRefundMessage, PayRefundMessage.Result> {
    public WxPayRefundMessageRule(WxPayService payService) {
        super(new StringConvert<PayRefundMessage>() {
            @Override
            public <R extends PayRefundMessage> R convert(String body, Class<R> clazz) {
                return payService.message(body, clazz);
            }
        }, WxPayRefundMessageRule::handleMessage);
    }

    protected static PayRefundMessage.Result handleMessage(PayRefundMessage message) {
        try {
            final PayRefundMessage.ReqInfo reqInfo = message.getReqInfo();
            final String refundTradeNo = reqInfo.getRefundId();
            final String refundSn = reqInfo.getOutTradeNo();
            SpringContext.getBean(PaymentService.class).handleRefundCallback(PaymentWay.WxPayWithJSAPI, refundSn, refundTradeNo, true);
            return PayRefundMessage.Result.builder().returnMsg("SUCCESS").returnCode("OK").build();
        } catch (Exception e) {
            return PayRefundMessage.Result.builder().returnMsg("FAIL").returnCode("FAIL").build();
        }
    }
}
