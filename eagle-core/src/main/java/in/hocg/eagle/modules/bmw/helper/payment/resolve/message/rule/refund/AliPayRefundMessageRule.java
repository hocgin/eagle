package in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.refund;

import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.message.TradeStatusSyncMessage;
import in.hocg.payment.convert.StringConvert;
import in.hocg.payment.resolve.StringResolve;
import in.hocg.payment.wxpay.v2.message.PayRefundMessage;

import java.util.Map;

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

    protected static TradeStatusSyncMessage.Result handleMessage(TradeStatusSyncMessage message, Map<String, Object> args) {
        try {
            return TradeStatusSyncMessage.Result.builder().result("fail").build();
        } catch (Exception e) {
            return TradeStatusSyncMessage.Result.builder().result("fail").build();
        }
    }
}
