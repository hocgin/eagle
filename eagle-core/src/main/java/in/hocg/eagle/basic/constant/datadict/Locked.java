package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("锁定状态")
@RequiredArgsConstructor
public enum Locked implements IntEnum {
    Off(0, "已锁定"),
    On(1, "正常");
    private final Integer code;
    private final String name;
}
