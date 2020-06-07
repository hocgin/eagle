package in.hocg.eagle.modules.bmw.helper.payment.resolve.message;

import in.hocg.eagle.basic.constant.datadict.PaymentNotifyType;
import in.hocg.eagle.basic.constant.datadict.PaymentPlatform;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Created by hocgin on 2019/12/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@RequiredArgsConstructor
public enum MessageType {
    WxPayWithPayment(PaymentPlatform.WxPay.getCode(), PaymentNotifyType.Trade.getCode(), "支付通知 - 微信"),
    WxPayWithRefund(PaymentPlatform.WxPay.getCode(), PaymentNotifyType.Refund.getCode(), "退款通知 - 微信"),
    AliPayWithPayment(PaymentPlatform.AliPay.getCode(), PaymentNotifyType.Trade.getCode(), "支付通知 - 支付宝"),
    AliPayWithRefund(PaymentPlatform.AliPay.getCode(), PaymentNotifyType.Refund.getCode(), "退款通知 - 支付宝"),
    ;

    private final Integer channel;
    private final Integer feature;
    private final String name;

    public static Optional<MessageType> of(@NonNull Integer channel, @NonNull Integer feature) {
        for (MessageType type : MessageType.values()) {
            if (type.channel.equals(channel) && type.feature.equals(feature)) {
                return Optional.ofNullable(type);
            }
        }
        return Optional.empty();
    }
}
