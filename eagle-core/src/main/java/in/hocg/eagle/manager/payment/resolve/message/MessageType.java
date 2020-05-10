package in.hocg.eagle.manager.payment.resolve.message;

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
    WxPay_PayRefund(1, 1, "支付通知 - 微信"),
    WxPay_UnifiedOrder(1, 2, "退款通知 - 微信"),
    AliPay_TradeStatusSync(2, 1, "支付通知 - 支付宝"),
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
