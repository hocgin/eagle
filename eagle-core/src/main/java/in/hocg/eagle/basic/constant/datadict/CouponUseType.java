package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.invoke.MethodHandles;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("使用类型")
@RequiredArgsConstructor
public enum CouponUseType implements DataDictEnum {
    Universal(0, "全场通用"),
    SpecifiedCategory(1, "指定品类"),
    DesignatedProduct(2, "指定商品");
    private final Integer code;
    private final String name;

    public static final String KEY = "couponUseType";
}
