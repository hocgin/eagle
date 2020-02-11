package in.hocg.eagle.basic.exception;

import in.hocg.java.basic.utils.TextBlock;

/**
 * Created by hocgin on 2019-09-24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class ServiceException extends RuntimeException {
    
    private ServiceException(String message) {
        super(message);
    }
    
    public static ServiceException wrap(String message, Object... args) {
        return new ServiceException(TextBlock.format(message, args));
    }
}
