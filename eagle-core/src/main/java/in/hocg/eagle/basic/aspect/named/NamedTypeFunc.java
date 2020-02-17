package in.hocg.eagle.basic.aspect.named;

import java.io.Serializable;
import java.util.function.BiFunction;

/**
 * Created by hocgin on 2020/2/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@FunctionalInterface
public interface NamedTypeFunc extends BiFunction<Object, String[], Object>, Serializable {
}
