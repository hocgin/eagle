package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/5/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("交易退款状态")
@RequiredArgsConstructor
public enum RefundStatus implements IntEnum {
    Init(0, "未退款"),
    Wait(1, "退款处理中"),
    Done(2, "退款成功"),
    Closed(3, "退款关闭"),
    Fail(4, "退款失败");
    private final Integer code;
    private final String name;

    public static final String KEY = "refundStatus";

    public PaymentNotifyStatus asPaymentNotifyStatus() {
        switch (this) {
            case Init:
                return PaymentNotifyStatus.Init;
            case Wait:
                return PaymentNotifyStatus.Pending;
            case Done:
                return PaymentNotifyStatus.Success;
            case Closed:
                return PaymentNotifyStatus.Closed;
            case Fail:
                return PaymentNotifyStatus.Fail;
            default:
                throw new IllegalArgumentException("转换失败");
        }
    }
}
