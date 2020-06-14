package in.hocg.web.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("是否默认")
@RequiredArgsConstructor
public enum IsDefault implements DataDictEnum {
    Off(0, "否"),
    On(1, "是");
    private final Integer code;
    private final String name;

    public static final String KEY = "isDefault";
}
