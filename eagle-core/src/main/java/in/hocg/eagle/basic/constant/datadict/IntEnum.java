package in.hocg.eagle.basic.constant.datadict;

import in.hocg.eagle.utils.LangUtils;

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

    default boolean eq(Integer val) {
        return LangUtils.equals(this.getCode(), val);
    }


    static <T extends IntEnum> Optional<T> of(Integer code, Class<T> enumClass) {
        final T[] constants = enumClass.getEnumConstants();
        for (T value : constants) {
            if (value.getCode().compareTo(code) == 0) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    static <T extends IntEnum> T ofThrow(Integer code, Class<T> enumClass) {
        final Optional<T> enumOpl = of(code, enumClass);
        if (enumOpl.isPresent()) {
            return enumOpl.get();
        } else {
            throw new IllegalArgumentException("未找到匹配的类型");
        }
    }


    static <T extends Enum<T>> Optional<T> of(String name, Class<T> enumClass) {
        return Optional.of(Enum.valueOf(enumClass, name));
    }
}
