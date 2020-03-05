package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 * 开关 [关, 开]
 *
 * @author hocgin
 */
@Getter
@ApiModel("开关")
@RequiredArgsConstructor
public enum Enabled implements IntEnum {
    Off(0, "禁用"),
    On(1, "启用");
    private final Integer code;
    private final String name;
}
