package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/7/2.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("订单优惠明细类型")
@RequiredArgsConstructor
public enum DiscountType implements DataDictEnum {
    Coupon(0, "优惠券优惠");
    private final Integer code;
    private final String name;

    public static final String KEY = "DiscountType";

}
