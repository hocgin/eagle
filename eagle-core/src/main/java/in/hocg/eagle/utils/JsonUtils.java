package in.hocg.eagle.utils;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;

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
    public static String toJSONString(Object object) {
        return JsonUtils.toJSONString(object, false);
    }

    public static String toJSONString(Object object, boolean pretty) {
        return JSON.toJSONString(object, pretty);
    }

}
