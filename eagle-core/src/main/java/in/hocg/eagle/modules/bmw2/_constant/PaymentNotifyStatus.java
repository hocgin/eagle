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
@ApiModel("支付网关通知接入应用状态")
@RequiredArgsConstructor
public enum PaymentNotifyStatus implements IntEnum {
    Init(0, "初始化"),
    Pending(1, "进行中"),
    Success(2, "成功"),
    Fail(3, "失败"),
    Closed(4, "关闭");
    private final Integer code;
    private final String name;
}
