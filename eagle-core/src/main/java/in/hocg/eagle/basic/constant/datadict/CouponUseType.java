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
@ApiModel("使用类型")
@RequiredArgsConstructor
public enum CouponUseType implements IntEnum {
    Universal(0, "全场通用"),
    SpecifiedCategory(1, "指定品类"),
    DesignatedProduct(2, "指定商品");
    private final Integer code;
    private final String name;

    @Override
    public Integer getCode() {
        return this.code;
    }
}
