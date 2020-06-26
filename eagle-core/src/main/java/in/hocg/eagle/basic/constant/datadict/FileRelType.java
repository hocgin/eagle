package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/6/26.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("上传文件类型")
@RequiredArgsConstructor
public enum  FileRelType implements DataDictEnum {
    Unknown(0, "未知"),
    Product(1, "商品图片");
    private final Integer code;
    private final String name;
    public static final String KEY = "FileRelType";
}
