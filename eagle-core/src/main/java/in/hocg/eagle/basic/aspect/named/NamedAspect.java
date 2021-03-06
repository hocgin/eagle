package in.hocg.eagle.basic.aspect.named;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.clazz.ClassUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.stream.Stream;

/**
 * @author hocgin
 * @date 2017/11/17
 * email: hocgin@gmail.com
 * 自动填充类型对应的名称
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class NamedAspect {
    private final ThreadLocal<Map<String, Object>> cache = ThreadLocal.withInitial(WeakHashMap::new);

    @Pointcut("@within(org.springframework.stereotype.Service) && execution((*) *(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        handleResult(result);
        return result;
    }

    private void handleResult(Object result) {
        if (Objects.isNull(result) || ClassUtils.isBaseType(result.getClass())) {
            return;
        }
        if (result instanceof IPage) {
            handlePageResult((IPage) result);
        } else if (result instanceof Collection) {
            handleCollectionResult((Collection) result);
        } else if (result instanceof Object[]) {
            handleArrayResult((Object[]) result);
        } else {
            handleObjectResult(result);
        }
    }

    private void handlePageResult(IPage result) {
        this.handleResult(result.getRecords());
    }

    private void handleArrayResult(Object[] result) {
        for (Object item : result) {
            handleResult(item);
        }
    }

    private void handleCollectionResult(Collection result) {
        result.forEach(this::handleResult);
    }

    private void handleObjectResult(Object result) {
        final Class<?> aClass = result.getClass();
        if (!aClass.isAnnotationPresent(InjectNamed.class)) {
            return;
        }
        InjectNamed injectNamed = aClass.getAnnotation(InjectNamed.class);

        Map<String, Field> fieldMap = LangUtils.toMap(ClassUtils.getAllField(aClass), Field::getName);

        Stream<Field> fieldStream;
        if (injectNamed.parallel()) {
            fieldStream = fieldMap.values().parallelStream();
        } else {
            fieldStream = fieldMap.values().stream();
        }
        fieldStream.peek(field -> {
            final Object object = ClassUtils.getObjectValue(result, field, null);
            handleResult(object);
        }).filter(
            field -> field.isAnnotationPresent(Named.class)
        ).filter(field -> {
            final Object value = ClassUtils.getObjectValue(result, field, null);
            return Objects.isNull(value);
        }).forEach(field -> injectValue(result, fieldMap, field));
    }

    private void injectValue(Object result, Map<String, Field> fieldMap, Field field) {
        final Named named = field.getAnnotation(Named.class);
        final Field idField = fieldMap.get(named.idFor());
        if (Objects.isNull(idField)) {
            return;
        }
        String[] argsValue = named.args();
        if (argsValue.length == 0) {
            argsValue = new String[]{named.idFor()};
        }
        final NamedType namedType = named.type();
        final Object id = ClassUtils.getObjectValue(result, idField, null);
        Object val;
        try {
            val = getValue(namedType, id, argsValue);
        } catch (Exception e) {
            val = null;
        }
        ClassUtils.setFieldValue(result, field, val);
    }

    private Object getValue(NamedType namedType, Object id, String[] args) {
        final String key = String.format("%s-%s-%s", namedType.name(), id, Arrays.toString(args));
        return cache.get().computeIfAbsent(key, s ->
            this.callNamedHandleMethod(namedType, id, args).orElse(null)
        );
    }

    private Optional<Object> callNamedHandleMethod(NamedType namedType, Object id, Object[] args) {
        final NamedService namedService = SpringContext.getBean(NamedService.class);
        for (Method method : NamedService.class.getMethods()) {
            final NamedHandler annotation = method.getAnnotation(NamedHandler.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            final NamedType value = annotation.value();
            if (value.equals(namedType)) {
                try {
                    return Optional.ofNullable(method.invoke(namedService, id, args));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }


}
