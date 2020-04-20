package in.hocg.eagle.manager.payment.resolve.message.rule;

import in.hocg.payment.convert.StringConvert;
import in.hocg.payment.resolve.StringResolve;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.message.PayRefundMessage;

import java.util.function.Function;

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
        }, new Function<PayRefundMessage, PayRefundMessage.Result>() {
            @Override
            public PayRefundMessage.Result apply(PayRefundMessage wxPayMessage) {
                // ..省略业务操作
                return PayRefundMessage.Result.builder()
                        .returnMsg("SUCCESS")
                        .returnCode("OK")
                        .build();
            }
        });

    }
}
