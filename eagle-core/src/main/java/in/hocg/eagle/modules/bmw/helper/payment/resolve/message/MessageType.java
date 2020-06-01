package in.hocg.eagle.modules.bmw.helper.payment.resolve.message;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2019/12/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@RequiredArgsConstructor
public enum MessageType {
    WxPay_Payment(1, 1, "支付通知 - 微信"),
    WxPay_Refund(1, 2, "退款通知 - 微信"),
    AliPay_Payment(2, 1, "支付通知 - 支付宝"),
    AliPay_Refund(2, 2, "退款通知 - 支付宝"),
    ;

    private final Integer channel;
    private final Integer feature;
    private final String name;

    public static MessageType of(@NonNull Integer channel,
                                 @NonNull Integer feature) {
        for (MessageType type : MessageType.values()) {
            if (type.channel.equals(channel) && type.feature.equals(feature)) {
                return type;
            }
        }
        throw new UnsupportedOperationException();
    }
}
