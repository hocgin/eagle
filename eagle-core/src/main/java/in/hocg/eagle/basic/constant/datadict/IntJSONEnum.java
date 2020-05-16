package in.hocg.eagle.basic.constant.datadict;

import com.alibaba.fastjson.JSON;
import in.hocg.eagle.utils.string.JsonUtils;

import java.util.List;

/**
 * Created by hocgin on 2020/5/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface IntJSONEnum extends IntEnum {

    default <T> T asClass(String json, Class<T> clazz) {
        if (List.class.isAssignableFrom(clazz)) {
            final Class<?> aClass = clazz.getClasses()[0];
            return (T) JSON.parseArray(json, aClass);
        }
        return JsonUtils.parseObject(json, clazz);
    }
}
