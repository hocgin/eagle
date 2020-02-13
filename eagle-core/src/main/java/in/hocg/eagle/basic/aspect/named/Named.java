package in.hocg.eagle.basic.aspect.named;

import java.lang.annotation.*;

/**
 * Created by hocgin on 2020/2/13.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Named {
    String type() default "";
    String idField() default "";
}
