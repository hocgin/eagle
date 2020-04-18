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
@ApiModel("群成员来源")
@RequiredArgsConstructor
public enum GroupMemberSource implements IntEnum {
    All(0, "所有"),
    Custom(1, "自定义组员列表");
    private final Integer code;
    private final String name;

    public static final String KEY = "groupMemberSource";
}
