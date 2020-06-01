package in.hocg.eagle.modules.bmw.helper.payment.request;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.bmw.helper.payment.dto.PaymentWay;
import in.hocg.payment.PaymentResponse;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.request.AliPayRequest;
import in.hocg.payment.alipay.v2.request.TradeAppPayRequest;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.request.UnifiedOrderRequest;
import in.hocg.payment.wxpay.v2.request.WxPayRequest;
import in.hocg.payment.wxpay.v2.response.UnifiedOrderResponse;
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
public class PaymentRequest extends AbsRequest {
    @ApiModelProperty("支付方式")
    private final PaymentWay paymentWay;
    @ApiModelProperty("订单支付金额")
    private final BigDecimal payAmount;
    @ApiModelProperty("订单号")
    private final String orderSn;

    private String getSubject() {
        return String.format("订单: %s", this.orderSn);
    }

    private AliPayRequest aliPayRequest() {
        TradeAppPayRequest request = new TradeAppPayRequest();
        request.setBizContent2(new TradeAppPayRequest.BizContent()
            .setSubject(this.getSubject())
            .setTotalAmount(String.valueOf(this.getPayAmount()))
            .setOutTradeNo(this.getOrderSn()));
        request.setNotifyUrl(String.format("%s/2/1", this.getHost()));
        return request;
    }

    private WxPayRequest wxPayRequest() {
        UnifiedOrderRequest request = new UnifiedOrderRequest();
        request.setOpenId("opQx55EOxwwO8kyQKrQePlHTOBAg");
        request.setTradeType("JSAPI");
        request.setBody(this.getSubject());
        request.setNotifyUrl(String.format("%s/1/1", this.getHost()));
        request.setOutTradeNo(this.getOrderSn());
        request.setTotalFee(String.valueOf(this.getPayAmount().multiply(BigDecimal.valueOf(100L))));
        request.setSpbillCreateIp(this.getClientIp());
        return request;
    }

    public PaymentRequestResult request() {
        final Integer paymentWayCode = paymentWay.getCode();
        final PaymentRequestResult result = new PaymentRequestResult();
        if (PaymentWay.WxPay.eq(paymentWayCode)) {
            final UnifiedOrderResponse response = SpringContext.getBean(WxPayService.class).request(this.wxPayRequest());
            return result.setForm(response.getContent());
        } else if (PaymentWay.AliPay.eq(paymentWayCode)) {
            final PaymentResponse response = SpringContext.getBean(AliPayService.class).request(this.aliPayRequest());
            return result.setForm(response.getContent());
        }
        throw ServiceException.wrap("暂不支持该支付方式");
    }
}
