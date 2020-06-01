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
@ApiModel("通知事件状态")
@RequiredArgsConstructor
public enum NotifyEventStatus implements IntEnum {
    Init(0, "初始化"),
    Pending(1, "进行中"),
    Success(2, "成功"),
    Fail(3, "失败");
    private final Integer code;
    private final String name;
}
