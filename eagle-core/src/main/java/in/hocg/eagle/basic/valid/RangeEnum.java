package in.hocg.eagle.basic.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = RangeEnumValidator.class)
@Documented
public @interface RangeEnum {
    
    String message() default "参数错误";
    
    Class<? extends Enum>[] enumClass() default {};
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
