package in.hocg.eagle.manager.payment;

import in.hocg.eagle.basic.constant.datadict.OrderPayType;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.manager.payment.dto.PayOrderDto;
import in.hocg.eagle.manager.payment.resolve.message.AllInMessageResolve;
import in.hocg.eagle.manager.payment.resolve.message.MessageType;
import in.hocg.payment.PaymentMessage;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.wxpay.v2.WxPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final WxPayService wxPayService;
    private final AliPayService aliPayService;
    private final AllInMessageResolve messageResolve;

    @Transactional(rollbackFor = Exception.class)
    public String payOrder(PayOrderDto dto) {
        final OrderPayType payType = dto.getPayType();
        if (OrderPayType.WxPay.equals(payType)) {
            return wxPayService.request(dto.wxPayRequest()).getContent();
        } else if (OrderPayType.AliPay.equals(payType)) {
            return aliPayService.request(dto.aliPayRequest()).getContent();
        }
        throw ServiceException.wrap("操作失败");
    }

    @Transactional(rollbackFor = Exception.class)
    public String doPayResultMessage(Integer channel, Integer feature, String data) {
        // TODO 异步处理通知
        // TODO 需要校验订单
        // TODO 检查回调类型
        PaymentMessage.Result result = messageResolve.handle(MessageType.of(channel, feature), data);
        return result.string();
    }

}
