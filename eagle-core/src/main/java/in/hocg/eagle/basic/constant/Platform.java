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
public enum Platform {
    Eagle(0, "Eagle 后台");
    private final int code;
    private final String name;
}
