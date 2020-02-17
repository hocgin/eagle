package in.hocg.eagle.basic.aspect.named;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.utils.ClassUtils;
import in.hocg.eagle.utils.LangUtils;
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
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

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
    private final NamedService namedService;
    
    @Pointcut("execution((@in.hocg.eagle.basic.aspect.named.InjectNamed *) *(..))")
    public void pointcut() {
    }
    
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        handleResult(result);
        return result;
    }
    
    private void handleResult(Object result) {
        if (Objects.isNull(result)) {
            return;
        }
        if (!result.getClass().isAnnotationPresent(InjectNamed.class)) {
            return;
        }
        if (result instanceof Page) {
            handlePageResult((Page) result);
        } else if (result instanceof Collection) {
            handleCollectionResult((Collection) result);
        } else if (result instanceof Object[]) {
            handleArrayResult((Object[]) result);
        } else {
            handleObjectResult(result);
        }
    }
    
    private void handlePageResult(Page result) {
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
        
        Map<String, Field> fieldMap = LangUtils.toMap(ClassUtils.getAllField(aClass), Field::getName);
        fieldMap.values().stream()
                .peek(field -> {
                    final Object object = ClassUtils.getObjectValue(result, field, null);
                    handleResult(object);
                })
                .filter(field -> field.isAnnotationPresent(Named.class))
                .forEach(field -> injectValue(result, fieldMap, field));
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
        
        try {
            Class<?>[] args = {Object.class, String[].class};
    
            final Method method = NamedService.class.getDeclaredMethod(namedType.getMethod(), args);
            Object val = method.invoke(namedService, id, argsValue);
            ClassUtils.setFieldValue(result, field, val);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.warn("Use @Named Error:", e);
        }
    }
    
}
