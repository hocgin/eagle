package in.hocg.web.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/3/26.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("性别")
@RequiredArgsConstructor
public enum Gender implements DataDictEnum {
    Female(0, "女"),
    Man(1, "男");
    private final Integer code;
    private final String name;

    public static final String KEY = "gender";
}
