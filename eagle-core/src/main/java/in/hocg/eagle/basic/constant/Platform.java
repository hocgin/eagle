package in.hocg.eagle.basic.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@RequiredArgsConstructor
public enum Platform implements IntEnum {
    Eagle(0, "Eagle 后台");
    private final Integer code;
    private final String name;
}
