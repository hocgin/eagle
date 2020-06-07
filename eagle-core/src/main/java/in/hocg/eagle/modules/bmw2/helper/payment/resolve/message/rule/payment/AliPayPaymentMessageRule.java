package in.hocg.eagle.modules.bmw2.helper.payment.resolve.message.rule.payment;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.constant.datadict.TradeStatus;
import in.hocg.eagle.modules.bmw2.helper.payment.resolve.message.MessageContext;
import in.hocg.eagle.modules.bmw2._constant.PaymentWay;
import in.hocg.eagle.modules.bmw2.pojo.ro.PaymentMessageRo;
import in.hocg.eagle.modules.bmw2.service.PaymentService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.lambda.map.LambdaMap;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.message.TradeStatusSyncMessage;
import in.hocg.payment.convert.StringConvert;
import in.hocg.payment.resolve.StringResolve;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

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

    protected static TradeStatusSyncMessage.Result handleMessage(TradeStatusSyncMessage message, Map<String, Object> args) {
        try {
            final LambdaMap<Object> lambdaMap = (LambdaMap<Object>) args;
            final String appid = lambdaMap.getAsString(MessageContext::getAppid);
            final Integer channel = lambdaMap.getAsInt(MessageContext::getChannel);
            final Integer feature = lambdaMap.getAsInt(MessageContext::getFeature);
            final PaymentWay paymentWay = IntEnum.of(lambdaMap.getAsInt(MessageContext::getPaymentWay), PaymentWay.class).orElse(PaymentWay.Unknown);

            final String outTradeNo = message.getOutTradeNo();
            final String tradeNo = message.getTradeNo();
            final TradeStatus tradeStatus = AliPayPaymentMessageRule.convertStatus(message.getTradeStatus());
            final LocalDateTime paymentAt = AliPayPaymentMessageRule.convertDatetime(message.getGmtPayment());
            final String totalAmount = message.getTotalAmount();
            final String buyerPayAmount = message.getBuyerPayAmount();
            final PaymentMessageRo ro = new PaymentMessageRo()
                .setAppid(LangUtils.getOrDefault(message.getAppId(), appid))
                .setTradeStatus(tradeStatus)
                .setTradeNo(tradeNo)
                .setTradeSn(outTradeNo)
                .setPaymentAt(paymentAt)
                .setTotalFee(new BigDecimal(totalAmount))
                .setBuyerPayFee(new BigDecimal(buyerPayAmount))
                .setChannel(channel)
                .setPaymentWay(paymentWay);

            SpringContext.getBean(PaymentService.class).handlePaymentMessage(ro);
            return TradeStatusSyncMessage.Result.builder().result("success").build();
        } catch (Exception e) {
            return TradeStatusSyncMessage.Result.builder().result("fail").build();
        }
    }

    private static TradeStatus convertStatus(String tradeStatus) {
        return null;
    }

    static LocalDateTime convertDatetime(String datetime) {
        return LocalDateTime.now();
    }
}
