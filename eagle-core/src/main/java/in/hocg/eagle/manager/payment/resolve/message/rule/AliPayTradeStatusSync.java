package in.hocg.eagle.manager.payment.resolve.message.rule;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.constant.datadict.OrderPayType;
import in.hocg.eagle.modules.oms.service.OrderService;
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
public class AliPayTradeStatusSync extends StringResolve.StringRule<TradeStatusSyncMessage, TradeStatusSyncMessage.Result> {
    public AliPayTradeStatusSync(AliPayService payService) {
        super(new StringConvert<TradeStatusSyncMessage>() {
            @Override
            public <R extends TradeStatusSyncMessage> R convert(String body, Class<R> clazz) {
                return payService.message(body, clazz);
            }
        }, message -> {
            final String orderSn = message.getOutTradeNo();
            SpringContext.getBean(OrderService.class).paySuccess(OrderPayType.AliPay.getCode(), orderSn);
            return TradeStatusSyncMessage.Result.builder().result("success").build();
        });

    }
}
