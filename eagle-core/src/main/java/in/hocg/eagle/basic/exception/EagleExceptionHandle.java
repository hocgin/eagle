package in.hocg.eagle.basic.exception;

import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.basic.result.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by hocgin on 2020/1/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@ControllerAdvice
public class EagleExceptionHandle extends DefaultExceptionHandler {
    
    @Override
    public Result<Void> handleException(Exception e) throws Exception {
        return super.handleException(e);
    }
    
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public Result<Void> handleAccessDeniedException(AccessDeniedException e) {
        final ResultCode resultCode = ResultCode.ACCESS_DENIED_ERROR;
        return Result.error(resultCode.getCode(), resultCode.getMessage());
    }
    
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Result<Void> handleAuthenticationException(AccessDeniedException e) {
        final ResultCode resultCode = ResultCode.AUTHENTICATION_ERROR;
        return Result.error(resultCode.getCode(), resultCode.getMessage());
    }
}
