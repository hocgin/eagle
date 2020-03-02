package in.hocg.eagle.basic.exception;

import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.basic.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;


/**
 * @author hocgin
 * @date 2018/6/18
 * email: hocgin@gmail.com
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
@ConditionalOnMissingBean(GlobalExceptionHandler.class)
public class DefaultExceptionHandler implements GlobalExceptionHandler {
    
    /**
     * 参数校验失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public Result<Void> handleBindException(Exception e) {
        final ResultCode resultCode = ResultCode.PARAMS_ERROR;
        FieldError fieldError = null;
        if (e instanceof BindException) {
            fieldError = ((BindException) e).getFieldError();
        } else if (e instanceof MethodArgumentNotValidException) {
            fieldError = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError();
        }
        String message = resultCode.getMessage();
        if (Objects.nonNull(fieldError)) {
            message = fieldError.getDefaultMessage();
        }
        return Result.error(resultCode.getCode(), message);
    }
    
    /**
     * 无法继续服务
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public Result<Void> handleServiceException(ServiceException e) {
        return Result.error(e.getCode(), e.getMessage());
    }
    
    /**
     * 系统错误
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Void> handleException(Exception e) throws Exception {
        log.error("系统错误", e);
        return Result.error();
    }
    
}
