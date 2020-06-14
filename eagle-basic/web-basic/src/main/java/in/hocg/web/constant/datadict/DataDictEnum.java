package in.hocg.web.constant.datadict;

import in.hocg.web.constant.CodeEnum;

/**
 * Created by hocgin on 2020/6/13.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface DataDictEnum extends CodeEnum {
    String KEY_FIELD_NAME = "KEY";

    String getName();

    static String getKey(Class<?> clazz) {
        return clazz.getSimpleName();
    }
}
