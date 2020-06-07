package in.hocg.eagle.modules.bmw2._constant;

import in.hocg.eagle.basic.constant.datadict.IntEnum;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/6/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("支付网关通知类型")
@RequiredArgsConstructor
public enum PaymentNotifyType implements IntEnum {
    Trade(0, "交易事件"),
    Refund(1, "退款事件"),;
    private final Integer code;
    private final String name;
}
