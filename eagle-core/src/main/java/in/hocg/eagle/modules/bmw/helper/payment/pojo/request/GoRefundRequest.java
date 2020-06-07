package in.hocg.eagle.modules.bmw.helper.payment.pojo.request;

import in.hocg.eagle.basic.constant.datadict.PaymentPlatform;
import in.hocg.eagle.basic.constant.datadict.PaymentWay;
import in.hocg.eagle.modules.bmw.helper.payment.pojo.response.GoRefundResponse;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.FeatureType;
import in.hocg.payment.PaymentService;
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
import lombok.NonNull;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/5/31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Builder
public class GoRefundRequest extends AbsRequest {
    @NonNull
    @ApiModelProperty(value = "支付平台AppId", required = true)
    private String platformAppid;
    @ApiModelProperty("退款方式")
    private final PaymentWay paymentWay;
    @ApiModelProperty("交易单号(网关)")
    private String tradeSn;
    @ApiModelProperty("交易流水号(第三方)")
    private final String tradeNo;
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
        request.setNotifyUrl(this.getNotifyUrl());
        return request;
    }

    private WxPayRequest wxRefundRequest() {
        final String refundAccount = String.valueOf(this.getRefundFee().multiply(BigDecimal.valueOf(100L)));
        final String totalFe = String.valueOf(this.getTotalFee().multiply(BigDecimal.valueOf(100L)));

        PayRefundRequest request = new PayRefundRequest();
        request.setOutTradeNo(getTradeSn());
        request.setTransactionId(getTradeNo());
        request.setOutRefundNo(getRefundSn());
        request.setTotalFee(totalFe);
        request.setRefundAccount(refundAccount);
        request.setNotifyUrl(this.getNotifyUrl());
        return request;
    }

    public GoRefundResponse request() {
        final GoRefundResponse result = new GoRefundResponse();
        final PaymentPlatform platform = paymentWay.getPlatform();
        final PaymentService<?> payService = getPayService(platform, platformAppid);
        switch (platform) {
            case WxPay: {
                final WxPayRequest request = this.wxRefundRequest();
                final PayRefundResponse response = ((WxPayService) payService).request(request);
                result.setRefundTradeNo(response.getRefundId());
                break;
            }
            case AliPay: {
                final AliPayRequest request = this.aliRefundRequest();
                final TradeRefundResponse response = ((AliPayService) payService).request(request);
                result.setRefundTradeNo(response.getTradeNo());
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
        return result;
    }

    private String getNotifyUrl() {
        return this.getHost() + paymentWay.getNotifyUrl(FeatureType.Refund, this.getPlatformAppid());
    }
}
