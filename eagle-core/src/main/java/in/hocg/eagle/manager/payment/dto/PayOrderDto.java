package in.hocg.eagle.manager.payment.dto;

import in.hocg.eagle.basic.env.Env;
import in.hocg.eagle.basic.constant.datadict.OrderPayType;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.payment.alipay.v2.request.AliPayRequest;
import in.hocg.payment.alipay.v2.request.TradeAppPayRequest;
import in.hocg.payment.wxpay.v2.request.UnifiedOrderRequest;
import in.hocg.payment.wxpay.v2.request.WxPayRequest;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/4/20.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class PayOrderDto {
    private OrderPayType payType;
    private OrderComplexVo order;
    private String clientIp;

    private String getSubject() {
        return "EAGLE 订单";
    }

    private String getHost() {
        return Env.getConfigs().getHostname();
    }

    private String getOrderSn() {
        return order.getOrderSn();
    }

    private BigDecimal getPayAmount() {
        return order.getPayAmount();
    }

    public AliPayRequest aliPayRequest() {
        TradeAppPayRequest request = new TradeAppPayRequest();
        request.setBizContent2(new TradeAppPayRequest.BizContent()
            .setSubject(this.getSubject())
            .setTotalAmount(String.valueOf(this.getPayAmount()))
            .setOutTradeNo(this.getOrderSn()));
        request.setNotifyUrl(String.format("%s/2/1", this.getHost()));
        return request;
    }

    public WxPayRequest wxPayRequest() {
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
}
