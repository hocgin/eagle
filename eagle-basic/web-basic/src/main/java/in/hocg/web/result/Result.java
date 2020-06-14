package in.hocg.web.result;

import in.hocg.web.utils.string.JsonUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.io.Serializable;

/**
 * @author hocgin
 * @date 2017/10/14
 * email: hocgin@gmail.com
 * 响应结果对象
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {
    private Integer code;
    private Boolean success;
    private String message;
    private T data;

    private Result() {
    }

    public static Result get() {
        return new Result();
    }

    public static Result result(boolean isOk) {
        return isOk ? success() : error();
    }

    public static <T> Result<T> success(String message, T data) {
        return Result.result(Boolean.TRUE, ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> success(T data) {
        return Result.success(ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success() {
        return Result.success(null);
    }

    public static <T> Result<T> result(Boolean success, Integer code, String message) {
        return Result.result(success, code, message, null);
    }

    public static <T> Result<T> error(Integer errorCode, String message) {
        return Result.result(Boolean.FALSE, errorCode, message);
    }

    public static <T> Result<T> error(String message) {
        return Result.error(ResultCode.ERROR.getCode(), message);
    }

    public static <T> Result<T> error() {
        return Result.error(ResultCode.ERROR.getMessage());
    }

    public static <T> Result<T> result(Boolean success, Integer code, String message, T data) {
        Result<T> result = new Result<>();
        return result.setCode(code)
            .setSuccess(success)
            .setMessage(message)
            .setData(data);
    }

    public ResponseEntity<Result<T>> asResponseEntity() {
        return ResponseEntity.ok(this);
    }

    public String json() {
        return JsonUtils.toJSONString(this);
    }

    public static ResponseEntity<InputStreamResource> stream(InputStream is) {
        return ResponseEntity
            .ok()
            .headers(new HttpHeaders() {{
                add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
                add(HttpHeaders.PRAGMA, "no-cache");
                add(HttpHeaders.EXPIRES, "0");
            }})
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(new InputStreamResource(is));
    }
}
