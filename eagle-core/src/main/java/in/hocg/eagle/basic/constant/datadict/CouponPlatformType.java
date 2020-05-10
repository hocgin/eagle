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
@ApiModel("优惠券适用平台")
@RequiredArgsConstructor
public enum CouponPlatformType implements IntEnum {
    All(0, "所有"),
    APP(1, "APP"),
    PC(2, "PC");
    private final Integer code;
    private final String name;
    public static final String KEY = "couponPlatformType";
}
