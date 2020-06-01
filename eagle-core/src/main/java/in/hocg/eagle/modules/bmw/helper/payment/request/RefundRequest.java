package in.hocg.eagle.modules.bmw.helper.payment.request;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.bmw.helper.payment.dto.PaymentWay;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.request.AliPayRequest;
import in.hocg.payment.alipay.v2.request.TradeRefundRequest;
import in.hocg.payment.alipay.v2.response.TradeRefundResponse;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.request.PayRefundRequest;
import in.hocg.payment.wxpay.v2.request.WxPayRequest;
import in.hocg.payment.wxpay.v2.response.PayRefundResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/5/31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Builder
public class RefundRequest extends AbsRequest {
    @ApiModelProperty("退款方式")
    private final PaymentWay paymentWay;
    @ApiModelProperty("交易流水号(第三方)")
    private final String tradeNo;
    @ApiModelProperty("交易流水号")
    private final String transactionSn;
    @ApiModelProperty("交易总金额")
    private BigDecimal totalFee;
    @ApiModelProperty("退款流水号")
    private final String refundSn;
    @ApiModelProperty("退款金额")
    private final BigDecimal refundFee;

    private AliPayRequest aliRefundRequest() {
        TradeRefundRequest request = new TradeRefundRequest();
        request.setBizContent2(new TradeRefundRequest.BizContent()
            .setRefundAmount(String.valueOf(getRefundFee()))
            .setOutTradeNo(getRefundSn())
            .setTradeNo(getTradeNo()));
        request.setNotifyUrl(String.format("%s/2/1", this.getHost()));
        return request;
    }

    private WxPayRequest wxRefundRequest() {
        final String refundAccount = String.valueOf(this.getRefundFee().multiply(BigDecimal.valueOf(100L)));
        final String totalFe = String.valueOf(this.getTotalFee().multiply(BigDecimal.valueOf(100L)));

        PayRefundRequest request = new PayRefundRequest();
        request.setOutTradeNo(getTransactionSn());
        request.setTransactionId(getTradeNo());
        request.setOutRefundNo(getRefundSn());
        request.setTotalFee(totalFe);
        request.setRefundAccount(refundAccount);
        request.setNotifyUrl(String.format("%s/1/1", this.getHost()));
        return request;
    }

    public RefundRequestResult request() {
        final Integer paymentWayCode = paymentWay.getCode();
        final RefundRequestResult result = new RefundRequestResult();
        result.setPaymentWay(paymentWayCode);
        if (PaymentWay.WxPay.eq(paymentWayCode)) {
            final PayRefundResponse response = SpringContext.getBean(WxPayService.class).request(this.wxRefundRequest());
            return result.setRefundTradeNo(response.getRefundId());
        } else if (PaymentWay.AliPay.eq(paymentWayCode)) {
            final TradeRefundResponse response = SpringContext.getBean(AliPayService.class).request(this.aliRefundRequest());
            return result.setRefundTradeNo(response.getTradeNo());
        }
        throw ServiceException.wrap("暂不支持该交易方式");
    }
}
