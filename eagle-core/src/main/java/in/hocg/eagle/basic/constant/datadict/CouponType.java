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
@ApiModel("优惠券类型")
@RequiredArgsConstructor
public enum CouponType implements DataDictEnum {
    Credit(0, "满减"),
    Discount(1, "折扣");
    private final Integer code;
    private final String name;
    public static final String KEY = "couponType";
}
