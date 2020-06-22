package in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.refund;

import in.hocg.web.SpringContext;
import in.hocg.web.constant.CodeEnum;
import in.hocg.web.constant.datadict.RefundStatus;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.MessageContext;
import in.hocg.web.constant.datadict.PaymentWay;
import in.hocg.eagle.modules.bmw.pojo.ro.RefundMessageRo;
import in.hocg.web.utils.DateUtils;
import in.hocg.web.utils.lambda.map.LambdaMap;
import in.hocg.payment.convert.StringConvert;
import in.hocg.payment.resolve.StringResolve;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.message.PayRefundMessage;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

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

    protected static PayRefundMessage.Result handleMessage(PayRefundMessage message, Map<String, Object> args) {
        try {
            final LambdaMap<Object> lambdaMap = (LambdaMap<Object>) args;
            final String appid = lambdaMap.getAsString(MessageContext::getAppid);
            final Integer channel = lambdaMap.getAsInt(MessageContext::getPlatformTyp);
            final Integer feature = lambdaMap.getAsInt(MessageContext::getFeature);
            final PaymentWay paymentWay = CodeEnum.of(lambdaMap.getAsInt(MessageContext::getPaymentWay), PaymentWay.class).orElse(PaymentWay.Unknown);

            final PayRefundMessage.ReqInfo reqInfo = message.getReqInfo();
            final String refundTradeNo = reqInfo.getRefundId();
            final String refundSn = reqInfo.getOutTradeNo();
            final RefundStatus refundStatus = WxPayRefundMessageRule.convertRefundStatus(reqInfo.getRefundStatus());
            final BigDecimal refundFee = WxPayRefundMessageRule.convertBigDecimal(new BigDecimal(reqInfo.getRefundFee()));
            final BigDecimal settlementRefundFee = WxPayRefundMessageRule.convertBigDecimal(new BigDecimal(reqInfo.getSettlementRefundFee()));
            final LocalDateTime refundAt = WxPayRefundMessageRule.convertDatetime(reqInfo.getSuccessTime());
            final RefundMessageRo ro = new RefundMessageRo()
                .setRefundAt(refundAt)
                .setRefundFee(refundFee)
                .setRefundStatus(refundStatus)
                .setSettlementRefundFee(settlementRefundFee)
                .setRefundSn(refundSn)
                .setRefundTradeNo(refundTradeNo)
                .setAppid(appid)
                .setChannel(channel);

            SpringContext.getBean(in.hocg.eagle.modules.bmw.service.PaymentService.class).handleRefundMessage(ro);
            return PayRefundMessage.Result.builder().returnMsg("SUCCESS").returnCode("OK").build();
        } catch (Exception e) {
            return PayRefundMessage.Result.builder().returnMsg("FAIL").returnCode("FAIL").build();
        }
    }

    private static RefundStatus convertRefundStatus(@NonNull String refundStatus) {
        switch (refundStatus.toUpperCase()) {
            // 退款成功
            case "SUCCESS":{
                return RefundStatus.Success;
            }
            // 退款关闭
            case "REFUNDCLOSE":
            // 退款异常
            case "CHANGE": {
                return RefundStatus.Fail;
            }
            default:
                throw new UnsupportedOperationException("操作失败");
        }
    }

    private static BigDecimal convertBigDecimal(BigDecimal v) {
        return v.multiply(new BigDecimal(100L));
    }

    private static LocalDateTime convertDatetime(String datetime) {
        return DateUtils.format(datetime, DateUtils.DATE_FORMAT_2);
    }
}
