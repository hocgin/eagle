package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("确认状态")
@RequiredArgsConstructor
public enum ConfirmStatus implements IntEnum {
    Unconfirmed(0, "未确认"),
    Confirmed(1, "已确认");
    private final Integer code;
    private final String name;
}
