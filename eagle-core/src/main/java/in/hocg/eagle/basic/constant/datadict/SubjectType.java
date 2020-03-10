package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/3/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("订阅类型")
@RequiredArgsConstructor
public enum SubjectType implements IntEnum {
    Comment(0, "评论");
    private final Integer code;
    private final String name;
}
