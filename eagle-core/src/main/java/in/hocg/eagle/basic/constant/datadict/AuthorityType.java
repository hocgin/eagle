package in.hocg.eagle.basic.constant.datadict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/2/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@RequiredArgsConstructor
public enum AuthorityType implements IntEnum {
    
    Button(0, "按钮"),
    Menu(1, "菜单");
    private final Integer code;
    private final String name;
}
