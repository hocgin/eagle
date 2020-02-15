package in.hocg.eagle.utils;

import in.hocg.eagle.basic.result.ResultCode;
import io.jsonwebtoken.lang.Assert;
import lombok.experimental.UtilityClass;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class VerifyUtils {
    
    public static void notNull(Object object, String message) {
        Assert.notNull(object, message);
    }
    
    public static void notNull(Object object) {
        notNull(object, ResultCode.SERVICE_ERROR.getMessage());
    }
    
    public static void isNull(Object object, String message) {
        Assert.isNull(object, message);
    }
    
    public static void isNull(Object object) {
        isNull(object, ResultCode.SERVICE_ERROR.getMessage());
    }
    
    public static void isTrue(boolean expression, String message) {
        Assert.isTrue(expression, message);
    }
    
    public static void isTrue(boolean expression) {
        isTrue(expression, ResultCode.SERVICE_ERROR.getMessage());
    }
    
}
