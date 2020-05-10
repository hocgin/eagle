package in.hocg.eagle.manager.payment.resolve.message.rule;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.constant.datadict.OrderPayType;
import in.hocg.eagle.modules.oms.service.OrderService;
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
public class WxUnifiedOrderMessageRule extends StringResolve.StringRule<UnifiedOrderMessage, UnifiedOrderMessage.Result> {
    public WxUnifiedOrderMessageRule(WxPayService payService) {
        super(new StringConvert<UnifiedOrderMessage>() {
            @Override
            public <R extends UnifiedOrderMessage> R convert(String body, Class<R> clazz) {
                return payService.message(body, clazz);
            }
        }, message -> {
            SpringContext.getBean(OrderService.class).paySuccess(OrderPayType.WxPay.getCode(), message.getOutTradeNo());
            return UnifiedOrderMessage.Result.builder().returnMsg("SUCCESS").returnCode("OK").build();
        });

    }
}
