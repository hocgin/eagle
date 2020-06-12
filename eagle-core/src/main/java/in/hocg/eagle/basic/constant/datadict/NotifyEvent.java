package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/6/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("通知事件类型")
@RequiredArgsConstructor
public enum NotifyEvent implements IntEnum {
    Paid(0, "支付事件"),
    Refund(1, "退款事件"),
    Canceled(2, "取消事件");
    private final Integer code;
    private final String name;
}
