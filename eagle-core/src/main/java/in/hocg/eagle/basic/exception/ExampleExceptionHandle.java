package in.hocg.eagle.basic.exception;

import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.basic.result.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;

/**
 * Created by hocgin on 2020/1/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@ControllerAdvice
public class ExampleExceptionHandle extends DefaultExceptionHandler {
    
    @Override
    public Result handleException(Exception e) throws Exception {
        return super.handleException(e);
    }
    
    
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public Result handleAccessDeniedException(AccessDeniedException e) {
        final ResultCode resultCode = ResultCode.ACCESS_DENIED_ERROR;
        return Result.error(resultCode.getCode(), e.getMessage());
    }
}
