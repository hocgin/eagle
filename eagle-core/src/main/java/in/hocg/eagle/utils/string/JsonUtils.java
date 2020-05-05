package in.hocg.eagle.utils.string;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * Created by hocgin on 2019-09-24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class JsonUtils {

    /**
     * JSON 字符串
     *
     * @param object
     * @return
     */
    public String toJSONString(Object object) {
        return JsonUtils.toJSONString(object, false);
    }

    public String toJSONString(Object object, boolean pretty) {
        return JSON.toJSONString(object, pretty);
    }

    public <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }
    public <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

}
