package in.hocg.eagle.basic.constant;

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
@RequiredArgsConstructor
public enum Enabled {
    Off(0, "禁用"),
    On(1, "启用");
    private final Integer code;
    private final String name;
}
