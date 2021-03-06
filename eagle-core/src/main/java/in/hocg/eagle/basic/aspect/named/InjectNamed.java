package in.hocg.eagle.basic.aspect.named;

import java.lang.annotation.*;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectNamed {
    boolean parallel() default true;
}
