package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/4/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("业务日志变更类型")
@RequiredArgsConstructor
public enum ChangeLogChangeType implements IntEnum {
    Insert(0, "新增"),
    Update(1, "更新"),
    Delete(2, "删除");
    private final Integer code;
    private final String name;

    public static final String KEY = "changeLogChangeType";
}
