package in.hocg.eagle.modules.bmw.helper.payment.pojo.request;

import in.hocg.eagle.basic.constant.datadict.PaymentPlatform;
import in.hocg.eagle.modules.bmw.helper.payment.pojo.response.QueryTradeResponse;
import in.hocg.payment.PaymentService;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.request.AliPayRequest;
import in.hocg.payment.alipay.v2.request.TradeQueryRequest;
import in.hocg.payment.alipay.v2.response.TradeQueryResponse;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.request.OrderQueryRequest;
import in.hocg.payment.wxpay.v2.request.WxPayRequest;
import in.hocg.payment.wxpay.v2.response.OrderQueryResponse;
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
public class QueryTradeRequest extends AbsRequest {
    @NonNull
    @ApiModelProperty(value = "交易单号(第三方)", required = true)
    private String tradeNo;
    @NonNull
    @ApiModelProperty(value = "支付平台AppId", required = true)
    private String platformAppid;
    @NonNull
    @ApiModelProperty(value = "支付平台", required = true)
    private PaymentPlatform platform;

    public QueryTradeResponse request() {
        final QueryTradeResponse result = new QueryTradeResponse();
        final PaymentService<?> payService = getPayService(platform, platformAppid);
        switch (platform) {
            case WxPay:{
                final OrderQueryResponse response = ((WxPayService) payService).request(this.wxPayRequest());
                break;
            }
            case AliPay:{
                final TradeQueryResponse response = ((AliPayService) payService).request(this.aliPayRequest());
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
        return result;
    }


    private AliPayRequest aliPayRequest() {
        final TradeQueryRequest request = new TradeQueryRequest();
        request.setBizContent2(new TradeQueryRequest.BizContent().setTradeNo(tradeNo));
        return request;
    }

    private WxPayRequest wxPayRequest() {
        final OrderQueryRequest request = new OrderQueryRequest();
        request.setTransactionId(tradeNo);
        return request;
    }
}
