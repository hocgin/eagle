package in.hocg.web.constant.datadict;

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
public enum PaymentNotifyResult implements DataDictEnum {
    Init(0, "初始化"),
    Success(1, "响应成功"),
    Fail(2, "响应失败"),
    Timeout(3, "超时失败"),
    ;
    private final Integer code;
    private final String name;
    public static final String KEY = "PaymentNotifyResult";
}
