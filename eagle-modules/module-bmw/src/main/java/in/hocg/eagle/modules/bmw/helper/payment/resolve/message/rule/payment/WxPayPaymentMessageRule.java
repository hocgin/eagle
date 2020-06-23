package in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.payment;

import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.MessageContext;
import in.hocg.eagle.modules.bmw.pojo.ro.PaymentMessageRo;
import in.hocg.eagle.modules.bmw.service.PaymentService;
import in.hocg.payment.convert.StringConvert;
import in.hocg.payment.resolve.StringResolve;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.message.UnifiedOrderMessage;
import in.hocg.web.SpringContext;
import in.hocg.web.constant.CodeEnum;
import in.hocg.web.constant.datadict.PaymentWay;
import in.hocg.web.constant.datadict.TradeStatus;
import in.hocg.web.utils.DateUtils;
import in.hocg.web.utils.LangUtils;
import in.hocg.web.utils.lambda.map.LambdaMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

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

    protected static UnifiedOrderMessage.Result handleMessage(UnifiedOrderMessage message, Map<String, Object> args) {
        try {
            final LambdaMap<Object> lambdaMap = (LambdaMap<Object>) args;
            final String appid = lambdaMap.getAsString(MessageContext::getAppid);
            final Integer channel = lambdaMap.getAsInt(MessageContext::getPlatformTyp);
            final Integer feature = lambdaMap.getAsInt(MessageContext::getFeature);
            PaymentWay paymentWay = CodeEnum.of(lambdaMap.getAsInt(MessageContext::getPaymentWay), PaymentWay.class).orElse(PaymentWay.Unknown);

            final String outTradeNo = message.getOutTradeNo();
            final String tradeNo = message.getTransactionId();
            final TradeStatus tradeStatus = WxPayPaymentMessageRule.convertStatus(message.getResultCode());
            final LocalDateTime paymentAt = WxPayPaymentMessageRule.convertDatetime(message.getTimeEnd());
            paymentWay = LangUtils.getOrDefault(WxPayPaymentMessageRule.convertTradeType(message.getTradeType()), paymentWay);
            final BigDecimal totalAmount = WxPayPaymentMessageRule.convertBigDecimal(new BigDecimal(message.getTotalFee()));
            final BigDecimal buyerPayAmount = WxPayPaymentMessageRule.convertBigDecimal(new BigDecimal(message.getSettlementTotalFee()));
            final PaymentMessageRo ro = new PaymentMessageRo()
                .setAppid(LangUtils.getOrDefault(message.getAppId(), appid))
                .setTradeStatus(tradeStatus)
                .setTradeNo(tradeNo)
                .setTradeSn(outTradeNo)
                .setPaymentAt(paymentAt)
                .setTotalFee(totalAmount)
                .setBuyerPayFee(buyerPayAmount)
                .setPlatformType(channel)
                .setPaymentWay(paymentWay);

            SpringContext.getBean(PaymentService.class).handlePaymentMessage(ro);
            return UnifiedOrderMessage.Result.builder().returnMsg("SUCCESS").returnCode("OK").build();
        } catch (Exception e) {
            return UnifiedOrderMessage.Result.builder().returnMsg("FAIL").returnCode("FAIL").build();
        }
    }

    private static PaymentWay convertTradeType(String tradeType) {
        switch (tradeType.toUpperCase()) {
            case "JSAPI":
                return PaymentWay.WxPayWithJSAPI;
            case "NATIVE":
                return PaymentWay.WxPayWithNative;
            case "APP":
                return PaymentWay.WxPayWithApp;
            default:
                return PaymentWay.Unknown;

        }
    }

    private static BigDecimal convertBigDecimal(BigDecimal v) {
        return v.multiply(BigDecimal.valueOf(100L));
    }

    private static LocalDateTime convertDatetime(String datetime) {
        return DateUtils.format(datetime, DateUtils.DATE_FORMAT_2);
    }

    private static TradeStatus convertStatus(String resultCode) {
        switch (resultCode.toUpperCase()) {
            // 退款成功
            case "SUCCESS": {
                return TradeStatus.Success;
            }
            // 退款关闭
            case "FAIL":
            default:
                return TradeStatus.Fail;
        }
    }
}
