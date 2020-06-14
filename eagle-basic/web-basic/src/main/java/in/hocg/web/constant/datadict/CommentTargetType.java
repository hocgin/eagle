package in.hocg.web.constant.datadict;

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
@ApiModel("评论目标")
@RequiredArgsConstructor
public enum CommentTargetType implements DataDictEnum {
    Test(0, "测试");
    private final Integer code;
    private final String name;

    public static final String KEY = "CommentTargetType";
}
