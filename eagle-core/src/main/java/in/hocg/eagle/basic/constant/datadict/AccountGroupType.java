package in.hocg.eagle.basic.constant.datadict;

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
@ApiModel("账号群组类型")
@RequiredArgsConstructor
public enum AccountGroupType implements IntEnum {
    General(0, "通用");
    private final Integer code;
    private final String name;

    public static final String KEY = "accountGroupType";
}
