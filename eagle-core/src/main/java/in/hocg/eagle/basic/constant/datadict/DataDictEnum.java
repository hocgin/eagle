package in.hocg.eagle.basic.constant.datadict;

import in.hocg.eagle.basic.constant.CodeEnum;

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
