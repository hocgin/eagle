package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("删除状态")
@RequiredArgsConstructor
public enum DeleteStatus implements IntEnum {
    Off(0, "正常"),
    On(1, "已删除");
    private final Integer code;
    private final String name;

    @Override
    public Integer getCode() {
        return this.code;
    }
}
