package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/5/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("微信菜单类型")
@RequiredArgsConstructor
public enum WxMenuType implements DataDictEnum {
    General(0, "通用菜单"),
    Individuation(1, "个性化菜单");
    private final Integer code;
    private final String name;

    public static final String KEY = "wxMenuType";
}

