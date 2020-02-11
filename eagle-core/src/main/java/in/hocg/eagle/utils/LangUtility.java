package in.hocg.eagle.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

/**
 * Created by hocgin on 2020/1/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class LangUtility {
    
    public <T> T getOrDefault(T v, T def) {
        if (Objects.isNull(v)) {
            return def;
        }
        return v;
    }
}
