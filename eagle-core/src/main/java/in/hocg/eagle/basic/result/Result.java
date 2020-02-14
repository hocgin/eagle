package in.hocg.eagle.basic.result;

import in.hocg.eagle.utils.JSONUtility;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.ResponseEntity;

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
    private int code;
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
        return Result.result(ResultCode.SUCCESS.getCode(), message, data);
    }
    
    public static <T> Result<T> success(T data) {
        return Result.success(ResultCode.SUCCESS.getMessage(), data);
    }
    
    public static <T> Result<T> success() {
        return Result.success(null);
    }
    
    public static <T> Result<T> result(Integer code, String message) {
        return Result.result(code, message, null);
    }
    
    public static <T> Result<T> error(int errorCode, String message) {
        return Result.result(errorCode, message);
    }
    
    public static <T> Result<T> error(String message) {
        return Result.error(ResultCode.ERROR.getCode(), message);
    }
    
    public static <T> Result<T> error() {
        return Result.error(ResultCode.ERROR.getMessage());
    }
    
    public static <T> Result<T> result(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        return result.setCode(code)
                .setMessage(message)
                .setData(data);
    }
    
    public ResponseEntity<Result<T>> asResponseEntity() {
        return ResponseEntity.ok(this);
    }
    
    public String json() {
        return JSONUtility.toJSONString(this);
    }
}
