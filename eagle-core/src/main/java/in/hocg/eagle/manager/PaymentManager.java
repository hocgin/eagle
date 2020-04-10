package in.hocg.eagle.manager;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.constant.datadict.OrderPayType;
import in.hocg.eagle.basic.constant.datadict.OrderStatus;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.oms.pojo.qo.order.PayOrderQo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.OrderService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.web.RequestUtils;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.message.PayRefundMessage;
import in.hocg.payment.wxpay.v2.request.UnifiedOrderRequest;
import in.hocg.payment.wxpay.v2.response.UnifiedOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by hocgin on 2020/3/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentManager {
    private final OrderService orderService;
    private final WxPayService wxPayService;

    @Transactional(rollbackFor = Exception.class)
    public String payOrder(PayOrderQo qo) throws Throwable {
        final Optional<HttpServletRequest> request = SpringContext.getRequest();
        String clientIp = "127.0.0.1";
        if (request.isPresent()) {
            clientIp = RequestUtils.getClientIP(request.get());
        }
        final Long id = qo.getId();
        final Optional<OrderPayType> payTypeOpl = IntEnum.of(qo.getPayType(), OrderPayType.class);
        final OrderPayType payType = payTypeOpl.orElseThrow((Supplier<Throwable>) () -> ServiceException.wrap("错误的支付方式"));

        final OrderComplexVo orderComplex = orderService.selectOne(id);
        if (!LangUtils.equals(OrderStatus.PendingPayment.getCode(), orderComplex.getOrderStatus())) {
            throw ServiceException.wrap("操作失败，请检查订单的支付状态");
        }

        if (OrderPayType.WxPay.equals(payType)) {
            UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
            unifiedOrderRequest.setOpenId("opQx55EOxwwO8kyQKrQePlHTOBAg");
            unifiedOrderRequest.setTradeType("JSAPI");
            unifiedOrderRequest.setBody("支付订单");
            unifiedOrderRequest.setNotifyUrl("http://frp.remmoe.com:8080/wx/callback");
            unifiedOrderRequest.setOutTradeNo(orderComplex.getOrderSn());
            unifiedOrderRequest.setTotalFee("1");
            unifiedOrderRequest.setSpbillCreateIp(clientIp);
            final UnifiedOrderResponse response = wxPayService.request(unifiedOrderRequest);
            return response.getContent();
        }

        throw ServiceException.wrap("操作失败");
    }

    @Transactional(rollbackFor = Exception.class)
    public String doPayResultMessage(Integer channel, Integer feature, String data) {
        PayRefundMessage message = wxPayService.message(data, PayRefundMessage.class);
        log.debug("通知信息: {}", message);
        // TODO 异步处理通知
        // TODO 需要校验订单
        // TODO 检查回调类型
        final String outTradeNo = message.getReqInfo().getOutTradeNo();

        orderService.paySuccess(OrderPayType.WxPay.getCode(), outTradeNo);

        return PayRefundMessage.Result.builder()
            .returnCode("OK")
            .returnMsg("SUCCESS")
            .build().string();
    }

}
