package in.hocg.web.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("文件引用类型")
@RequiredArgsConstructor
public enum FileRelType implements DataDictEnum {
    Unknown(0, "未知"),
    Product(1, "商品图片");
    private final Integer code;
    private final String name;
}
