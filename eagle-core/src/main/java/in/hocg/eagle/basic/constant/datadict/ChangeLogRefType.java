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
@ApiModel("业务日志引用类型")
@RequiredArgsConstructor
public enum ChangeLogRefType implements DataDictEnum {
    OrderLog(0, "订单日志");
    private final Integer code;
    private final String name;

    public static final String KEY = "changeLogRefType";
}
