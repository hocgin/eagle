package in.hocg.eagle.modules.bmw2.helper.payment.request;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.bmw2._constant.PaymentWay;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.payment.PaymentResponse;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.request.AliPayRequest;
import in.hocg.payment.alipay.v2.request.TradeAppPayRequest;
import in.hocg.payment.alipay.v2.request.TradePreCreateRequest;
import in.hocg.payment.alipay.v2.request.TradeWapPayRequest;
import in.hocg.payment.alipay.v2.response.TradePreCreateResponse;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.request.UnifiedOrderRequest;
import in.hocg.payment.wxpay.v2.request.WxPayRequest;
import in.hocg.payment.wxpay.v2.response.UnifiedOrderResponse;
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
public class PaymentRequest extends AbsRequest {
    @NonNull
    @ApiModelProperty(value = "支付平台AppId", required = true)
    private String platformAppid;
    @NonNull
    @ApiModelProperty(value = "支付方式", required = true)
    private PaymentWay paymentWay;
    @NonNull
    @ApiModelProperty(value = "订单支付金额", required = true)
    private BigDecimal payAmount;
    @NonNull
    @ApiModelProperty(value = "订单号", required = true)
    private String outTradeSn;
    @ApiModelProperty(value = "[可选] 微信用户(微信支付必须)")
    private String wxOpenId;
    @ApiModelProperty(value = "[可选] (支付宝Wap支付)")
    private String quitUrl;

    private String getSubject() {
        return String.format("订单: %s", this.outTradeSn);
    }

    private AliPayRequest aliPayAppRequest() {
        TradeAppPayRequest request = new TradeAppPayRequest();
        request.setBizContent2(new TradeAppPayRequest.BizContent()
            .setSubject(this.getSubject())
            .setTotalAmount(String.valueOf(this.getPayAmount()))
            .setOutTradeNo(this.getOutTradeSn()));
        request.setNotifyUrl(this.getHost() + PaymentWay.AliPayWithApp.getNotifyUrl());
        return request;
    }

    private AliPayRequest aliPayWapRequest() {
        TradeWapPayRequest request = new TradeWapPayRequest();
        request.setBizContent2(new TradeWapPayRequest.BizContent()
            .setSubject(this.getSubject())
            .setTotalAmount(String.valueOf(this.getPayAmount()))
            .setProductCode("QUICK_WAP_WAY")
            .setQuitUrl(getQuitUrl())
            .setOutTradeNo(this.getOutTradeSn()));
        request.setNotifyUrl(this.getHost() + PaymentWay.AliPayWithWap.getNotifyUrl());
        return request;
    }

    private AliPayRequest aliPayPcRequest() {
        TradeWapPayRequest request = new TradeWapPayRequest();
        request.setBizContent2(new TradeWapPayRequest.BizContent()
            .setSubject(this.getSubject())
            .setTotalAmount(String.valueOf(this.getPayAmount()))
            .setProductCode("FAST_INSTANT_TRADE_PAY")
            .setOutTradeNo(this.getOutTradeSn()));
        request.setNotifyUrl(this.getHost() + PaymentWay.AliPayWithPC.getNotifyUrl());
        return request;
    }

    private AliPayRequest aliPayQrCodeRequest() {
        TradePreCreateRequest request = new TradePreCreateRequest();
        request.setBizContent2(new TradePreCreateRequest.BizContent()
            .setSubject(this.getSubject())
            .setTotalAmount(String.valueOf(this.getPayAmount()))
            .setOutTradeNo(this.getOutTradeSn()));
        request.setNotifyUrl(this.getHost() + PaymentWay.AliPayWithQrCode.getNotifyUrl());
        return request;
    }

    private WxPayRequest wxPayJSAPIRequest() {
        final String wxOpenId = this.getWxOpenId();
        ValidUtils.notNull(wxOpenId, "微信支付需要指定用户ID");

        UnifiedOrderRequest request = new UnifiedOrderRequest();
        request.setOpenId(wxOpenId);
        request.setTradeType("JSAPI");
        request.setBody(this.getSubject());
        request.setNotifyUrl(this.getHost() + PaymentWay.WxPayWithJSAPI.getNotifyUrl());
        request.setOutTradeNo(this.getOutTradeSn());
        request.setTotalFee(String.valueOf(this.getPayAmount().multiply(BigDecimal.valueOf(100L))));
        request.setSpbillCreateIp(this.getClientIp());
        return request;
    }

    private WxPayRequest wxPayAPPRequest() {
        final String wxOpenId = this.getWxOpenId();
        ValidUtils.notNull(wxOpenId, "微信支付需要指定用户ID");

        UnifiedOrderRequest request = new UnifiedOrderRequest();
        request.setTradeType("APP");
        request.setBody(this.getSubject());
        request.setNotifyUrl(this.getHost() + PaymentWay.WxPayWithJSAPI.getNotifyUrl());
        request.setOutTradeNo(this.getOutTradeSn());
        request.setTotalFee(String.valueOf(this.getPayAmount().multiply(BigDecimal.valueOf(100L))));
        request.setSpbillCreateIp(this.getClientIp());
        return request;
    }

    private WxPayRequest wxPayNativeRequest() {
        final String wxOpenId = this.getWxOpenId();
        ValidUtils.notNull(wxOpenId, "微信支付需要指定用户ID");

        UnifiedOrderRequest request = new UnifiedOrderRequest();
        request.setTradeType("APP");
        request.setBody(this.getSubject());
        request.setNotifyUrl(this.getHost() + PaymentWay.WxPayWithApp.getNotifyUrl());
        request.setOutTradeNo(this.getOutTradeSn());
        request.setTotalFee(String.valueOf(this.getPayAmount().multiply(BigDecimal.valueOf(100L))));
        request.setSpbillCreateIp(this.getClientIp());
        return request;
    }

    public PaymentRequestResult request() {
        final PaymentRequestResult result = new PaymentRequestResult()
            .setPlatform(paymentWay.getPlatform().getCode())
            .setPaymentWay(paymentWay.getCode());
        switch (paymentWay) {
            case AliPayWithApp: {
                final PaymentResponse response = SpringContext.getBean(AliPayService.class).request(this.aliPayAppRequest());
                return result.setApp(response.getContent());
            }
            case AliPayWithQrCode: {
                final TradePreCreateResponse response = SpringContext.getBean(AliPayService.class).request(this.aliPayQrCodeRequest());
                return result.setQrCode(response.getQrCode());
            }
            case AliPayWithPC: {
                final PaymentResponse response = SpringContext.getBean(AliPayService.class).request(this.aliPayPcRequest());
                result.setMethod("POST");
                return result.setForm(response.getContent());
            }
            case AliPayWithWap: {
                final PaymentResponse response = SpringContext.getBean(AliPayService.class).request(this.aliPayWapRequest());
                result.setMethod("POST");
                return result.setForm(response.getContent());
            }
            case WxPayWithApp: {
                final UnifiedOrderResponse response = SpringContext.getBean(WxPayService.class).request(this.wxPayAPPRequest());
                return result.setApp(response.getContent());
            }
            case WxPayWithJSAPI: {
                final WxPayRequest request = this.wxPayJSAPIRequest();
                final UnifiedOrderResponse response = SpringContext.getBean(WxPayService.class).request(request);
                return result.setWxJSApi(PaymentRequestResult.WxJSAPI.NEW("", response.getNonceStr(), response.getPrepayId(), request.getSignType(), response.getSign()));
            }
            case WxPayWithNative: {
                final UnifiedOrderResponse response = SpringContext.getBean(WxPayService.class).request(this.wxPayNativeRequest());
                return result.setWxNative(response.getContent());
            }
            default: {
                throw ServiceException.wrap("暂不支持该支付方式");
            }
        }
    }

}
