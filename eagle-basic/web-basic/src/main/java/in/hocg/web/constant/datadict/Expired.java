package in.hocg.web.constant.datadict;

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
@ApiModel("过期状态")
@RequiredArgsConstructor
public enum Expired implements DataDictEnum {
    Off(0, "已过期"),
    On(1, "正常");
    private final Integer code;
    private final String name;

    public static final String KEY = "expired";
}
