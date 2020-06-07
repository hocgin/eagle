package in.hocg.eagle.modules.bmw.helper.payment.pojo.request;

import in.hocg.eagle.basic.constant.datadict.PaymentPlatform;
import in.hocg.eagle.modules.bmw.helper.payment.pojo.response.CloseTradeResponse;
import in.hocg.payment.PaymentService;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.request.AliPayRequest;
import in.hocg.payment.alipay.v2.request.TradeCloseRequest;
import in.hocg.payment.alipay.v2.response.TradeCloseResponse;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.request.CloseOrderRequest;
import in.hocg.payment.wxpay.v2.request.WxPayRequest;
import in.hocg.payment.wxpay.v2.response.CloseOrderResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@ApiModel("关闭交易单")
public class CloseTradeRequest extends AbsRequest {
    @NonNull
    @ApiModelProperty(value = "交易单号(网关)", required = true)
    private String tradeSn;
    @NonNull
    @ApiModelProperty(value = "支付平台AppId", required = true)
    private String platformAppid;
    @NonNull
    @ApiModelProperty(value = "支付平台", required = true)
    private PaymentPlatform platform;


    public CloseTradeResponse request() {
        final CloseTradeResponse result = new CloseTradeResponse();
        final PaymentService<?> payService = getPayService(platform, platformAppid);
        switch (platform) {
            case WxPay: {
                final CloseOrderResponse response = ((WxPayService) payService).request(this.wxPayRequest());
                break;
            }
            case AliPay: {
                final TradeCloseResponse response = ((AliPayService) payService).request(this.aliPayRequest());
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
        return result;
    }


    private AliPayRequest aliPayRequest() {
        final TradeCloseRequest request = new TradeCloseRequest();
        request.setBizContent2(new TradeCloseRequest.BizContent().setOutTradeNo(getTradeSn()));
        return request;
    }

    private WxPayRequest wxPayRequest() {
        final CloseOrderRequest request = new CloseOrderRequest();
        request.setOutTradeNo(getTradeSn());
        return request;
    }
}
