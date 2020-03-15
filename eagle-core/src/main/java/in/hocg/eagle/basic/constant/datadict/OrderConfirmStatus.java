package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("确认收货状态")
@RequiredArgsConstructor
public enum OrderConfirmStatus implements IntEnum {
    Unconfirmed(0, "未确认"),
    Confirmed(1, "已确认");
    private final Integer code;
    private final String name;
}
