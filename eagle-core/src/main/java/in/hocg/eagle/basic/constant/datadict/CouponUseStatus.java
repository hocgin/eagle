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
@ApiModel("使用状态")
@RequiredArgsConstructor
public enum CouponUseStatus implements IntEnum {
    Unused(0, "未使用"),
    Used(1, "已使用"),
    Expired(2, "已过期");
    private final Integer code;
    private final String name;

    public static final String KEY = "couponUseStatus";
}
