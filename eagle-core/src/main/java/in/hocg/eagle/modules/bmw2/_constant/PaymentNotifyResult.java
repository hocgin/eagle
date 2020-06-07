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
@ApiModel("通知调用结果")
@RequiredArgsConstructor
public enum PaymentNotifyResult implements IntEnum {
    Init(0, "初始化"),
    Success(1, "响应成功"),
    Fail(2, "响应失败"),
    Timeout(3, "超时失败"),
    ;
    private final Integer code;
    private final String name;
}
