package in.hocg.web.aspect.named;

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


    /**
     * 字典项标识
     *
     * @return
     */
    String idFor();

    /**
     * 注入类型
     *
     * @return
     */
    NamedType type() default NamedType.DataDict;

    /**
     * 参数(默认为: {named.idFor()})
     *
     * @return
     */
    String[] args() default {};

}
