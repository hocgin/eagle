package in.hocg.web.utils.clazz;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by hocgin on 2020/2/13.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class ObjectMeta {
    @Getter
    private final Class<?> clazz;
    @Getter
    private final Map<String, Field> fieldMap = Maps.newHashMap();
    private final static Map<Class<?>, ObjectMeta> CACHED = Maps.newHashMap();

    private ObjectMeta(Class<?> clazz) {
        this.clazz = clazz;
        ClassUtils.getAllField(clazz).forEach(field -> {
            fieldMap.put(field.getName(), field);
        });
    }

    public static ObjectMeta from(Class<?> clazz) {
        return CACHED.computeIfAbsent(clazz, ObjectMeta::new);
    }

    public void setIfExist(Object target, String key, Object val) {
        try {
            Field field = fieldMap.get(key);
            if (Objects.isNull(field)) {
                return;
            }
            field.setAccessible(true);
            field.set(target, val);
        } catch (IllegalAccessException ignored) {
        }
    }

    public <R> Optional<R> get(Object target, String key) {
        Field field = fieldMap.get(key);
        if (Objects.isNull(field)) {
            return Optional.empty();
        }
        field.setAccessible(true);
        try {
            return (Optional<R>) Optional.ofNullable(field.get(target));
        } catch (IllegalAccessException ignored) {
            return Optional.empty();
        }
    }
}
