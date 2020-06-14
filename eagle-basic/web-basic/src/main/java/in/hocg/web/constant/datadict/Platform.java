package in.hocg.web.constant.datadict;

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
@ApiModel("平台类型")
@RequiredArgsConstructor
public enum Platform implements DataDictEnum {
    Eagle(0, "Eagle 后台");
    private final Integer code;
    private final String name;

    public static final String KEY = "Platform";
}
