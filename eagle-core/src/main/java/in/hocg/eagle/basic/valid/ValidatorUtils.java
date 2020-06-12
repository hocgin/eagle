package in.hocg.eagle.basic.valid;

import in.hocg.eagle.utils.clazz.ClassUtils;

import javax.validation.*;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class ValidatorUtils {

    public static ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    public static Validator getValidation() {
        return getValidatorFactory().getValidator();
    }

    public static <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return getValidation().validate(object, groups);
    }

    public static <T> Set<ConstraintViolation<T>> validateParameters(T object, Object[] parameterValues, Class<?>... groups) {
        final ExecutableValidator executableValidator = getValidation().forExecutables();
        final Method method = getMethod(object);
        return executableValidator.validateParameters(object, method, parameterValues, groups);
    }

    public static <T> Set<ConstraintViolation<T>> validateReturnValue(T object, Object returnValue, Class<?>... groups) {
        final ExecutableValidator executableValidator = getValidation().forExecutables();
        final Method method = getMethod(object);
        return executableValidator.validateReturnValue(object, method, returnValue);
    }

    public static <T> void validThrow(T object, Class<?>... groups) {
        final Set<ConstraintViolation<T>> validate = ValidatorUtils.validate(object, groups);
        for (ConstraintViolation<T> violation : validate) {
            throw new ValidationException(violation.getMessage());
        }
    }

    private static <T> Method getMethod(T object) {
        final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        final String methodName = stackTraceElement.getMethodName();
        return ClassUtils.getMethod(object.getClass(), methodName);
    }

}
