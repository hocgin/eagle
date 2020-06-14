package in.hocg.web.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("订单来源")
@RequiredArgsConstructor
public enum OrderSourceType implements DataDictEnum {
    Unknown(0, "未知"),
    APP(1, "APP"),
    PC(2, "PC");
    private final Integer code;
    private final String name;

    public static final String KEY = "orderSourceType";
}
