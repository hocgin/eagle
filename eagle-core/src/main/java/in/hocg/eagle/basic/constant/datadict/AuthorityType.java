package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/2/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("权限类型")
@RequiredArgsConstructor
public enum AuthorityType implements DataDictEnum {
    Button(0, "按钮"),
    Menu(1, "菜单");
    private final Integer code;
    private final String name;

    public static final String KEY = "authorityType";
}
