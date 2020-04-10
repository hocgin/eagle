package in.hocg.eagle.basic.exception;

import in.hocg.eagle.basic.result.ResultCode;
import in.hocg.eagle.utils.string.TextBlock;
import lombok.Getter;

/**
 * Created by hocgin on 2019-09-24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class ServiceException extends RuntimeException {
    @Getter
    private final int code;

    private ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public static ServiceException wrap(String message, Object... args) {
        final int code = ResultCode.SERVICE_ERROR.getCode();
        return new ServiceException(code, TextBlock.format(message, args));
    }

    public static ServiceException wrap(int code, String message, Object... args) {
        return new ServiceException(code, TextBlock.format(message, args));
    }

}
