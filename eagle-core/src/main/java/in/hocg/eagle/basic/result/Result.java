package in.hocg.eagle.basic.result;

import com.sun.webkit.network.URLs;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.UrlUtils;
import in.hocg.eagle.utils.string.JsonUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

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

    public static ResponseEntity notFound() {
        return ResponseEntity.notFound().build();
    }

    public static ResponseEntity<InputStreamResource> stream(InputStream is) {
        if (Objects.isNull(is)) {
            return Result.notFound();
        }

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

    public static ResponseEntity<FileSystemResource> file(File file) {
        if (Objects.isNull(file) || !file.exists()) {
            return Result.notFound();
        }

        return ResponseEntity
            .ok()
            .headers(new HttpHeaders() {{
                add(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + LangUtils.getOrDefault(file.getName(), "Unknown"));
                add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
                add(HttpHeaders.PRAGMA, "no-cache");
                add(HttpHeaders.EXPIRES, "0");
            }})
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(new FileSystemResource(file));
    }

    public static ResponseEntity<InputStreamResource> url(String url) {
        try {
            return Result.url(URLs.newURL(url));
        } catch (MalformedURLException e) {
            throw ServiceException.wrap("非法链接");
        }
    }

    public static ResponseEntity<InputStreamResource> url(URL url) {
        if (Objects.isNull(url)) {
            return Result.notFound();
        }
        try {
            return stream(UrlUtils.toInputStream(url));
        } catch (IOException e) {
            throw ServiceException.wrap("读取链接异常");
        }
    }
}
