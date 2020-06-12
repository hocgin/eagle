package in.hocg.eagle.modules.bmw.helper.payment.pojo.request;

import in.hocg.eagle.basic.constant.datadict.PaymentPlatform;
import in.hocg.eagle.modules.bmw.helper.payment.pojo.response.QueryRefundResponse;
import in.hocg.payment.alipay.v2.request.AliPayRequest;
import in.hocg.payment.alipay.v2.request.TradeRefundRequest;
import in.hocg.payment.alipay.v2.response.TradeRefundResponse;
import in.hocg.payment.wxpay.v2.request.RefundQueryRequest;
import in.hocg.payment.wxpay.v2.request.WxPayRequest;
import in.hocg.payment.wxpay.v2.response.RefundQueryResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryRefundRequest extends AbsRequest {
    @NonNull
    @ApiModelProperty(value = "退款单号(第三方)", required = true)
    private String refundTradeNo;
    @NonNull
    @ApiModelProperty(value = "支付平台AppId", required = true)
    private String platformAppid;
    @NonNull
    @ApiModelProperty(value = "支付平台", required = true)
    private PaymentPlatform platform;

    public QueryRefundResponse request() {
        final QueryRefundResponse result = new QueryRefundResponse();
        switch (platform) {
            case WxPay: {
                final RefundQueryResponse response = this.request(this.wxPayRequest());
                break;
            }
            case AliPay: {
                final TradeRefundResponse response = this.request(this.aliPayRequest());
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
        return result;
    }


    private AliPayRequest aliPayRequest() {
        final TradeRefundRequest request = new TradeRefundRequest();
        request.setBizContent2(new TradeRefundRequest.BizContent().setTradeNo(refundTradeNo));
        return request;
    }

    private WxPayRequest wxPayRequest() {
        final RefundQueryRequest request = new RefundQueryRequest();
        request.setRefundId(refundTradeNo);
        return request;
    }

    @Override
    protected PaymentPlatform getPaymentPlatform() {
        return this.platform;
    }
}
