package in.hocg.eagle.basic.result;

import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.utils.UrlUtils;
import in.hocg.eagle.utils.string.JsonUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;

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

    public static <T> ResponseEntity download(T t, String filename) {
        Resource resource = Result.asResource(t);

        return ResponseEntity
            .ok()
            .headers(new HttpHeaders() {{
                setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());
                setCacheControl(CacheControl.noCache());
                setPragma("no-cache");
                setExpires(0L);
            }})
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
    }

    public static <T> ResponseEntity download(T t) {
        return Result.download(t, null);
    }

    public static <T> ResponseEntity preview(T t) {
        return Result.preview(t, null);
    }

    public static <T> ResponseEntity preview(T t, MediaType mediaType) {
        Resource resource = Result.asResource(t);

        return ResponseEntity
            .ok()
            .headers(new HttpHeaders() {{
                setContentDisposition(ContentDisposition.builder("inline").build());
                setCacheControl(CacheControl.noCache());
                setPragma("no-cache");
                setExpires(0L);
            }})
            .contentType(mediaType)
            .body(resource);
    }

    private static <T> Resource asResource(T t) {
        Resource resource;
        if (t instanceof InputStream) {
            resource = new InputStreamResource((InputStream) t);
        } else if (t instanceof Resource) {
            resource = (Resource) t;
        } else if (t instanceof File) {
            resource = new FileSystemResource((File) t);
        } else if (t instanceof URI || t instanceof String) {
            URI uri;
            if (t instanceof String) {
                uri = URI.create(((String) t));
            } else {
                uri = (URI) t;
            }
            try {
                resource = new InputStreamResource(UrlUtils.toInputStream(uri));
            } catch (IOException e) {
                throw ServiceException.wrap("读取链接异常");
            }
        } else {
            throw new UnsupportedOperationException();
        }
        return resource;
    }
}
