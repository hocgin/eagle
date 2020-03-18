package in.hocg.eagle.basic.constant.datadict;

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
public enum CouponPlatformType implements IntEnum {
    APP(1, "APP"),
    PC(2, "PC");
    private final Integer code;
    private final String name;

    @Override
    public Integer getCode() {
        return code;
    }
}
