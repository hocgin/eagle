package in.hocg.basic.named;

import java.lang.annotation.*;

/**
 * Created by hocgin on 2020/6/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NamedHandler {
    NamedType value();
}
