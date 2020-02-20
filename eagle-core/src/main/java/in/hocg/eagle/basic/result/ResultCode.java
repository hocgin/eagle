package in.hocg.eagle.basic.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2018/12/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@RequiredArgsConstructor
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200, "ok"),
    /**
     * 参数校验失败
     */
    PARAMS_ERROR(501, "参数校验失败"),
    /**
     * 服务处理失败
     */
    SERVICE_ERROR(502, "系统繁忙，请稍后"),
    /**
     * 拒绝访问(未登录)
     */
    AUTHENTICATION_ERROR(503, "拒绝访问"),
    /**
     * 无权访问
     */
    ACCESS_DENIED_ERROR(504, "无权访问"),
    /**
     * 默认错误码
     */
    ERROR(500, "error");
    private final int code;
    private final String message;
}
