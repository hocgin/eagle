package in.hocg.eagle.basic.constant.datadict;

import java.util.Optional;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface IntEnum {

    /**
     * 枚举值
     *
     * @return
     */
    Integer getCode();


    static <T extends IntEnum> Optional<T> of(Integer code, Class<T> enumClass) {
        final T[] constants = enumClass.getEnumConstants();
        for (T value : constants) {
            if (value.getCode().compareTo(code) == 0) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }


    static <T extends Enum<T>> Optional<T> of(String name, Class<T> enumClass) {
        return Optional.of(Enum.valueOf(enumClass, name));
    }
}
