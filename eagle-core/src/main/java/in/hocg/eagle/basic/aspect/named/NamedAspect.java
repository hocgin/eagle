package in.hocg.eagle.basic.aspect.named;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
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
                .forEach(field -> {
                    final Named named = field.getAnnotation(Named.class);
                    final Field idField = fieldMap.get(named.idField());
                    final String type = Strings.isNullOrEmpty(named.type()) ? named.idField() : named.type();
                    if (Objects.nonNull(idField)) {
                        String codeValue = LangUtils.toString(ClassUtils.getObjectValue(result, idField, null));
                        Object val = namedService.selectOneByTypeAndId(type, codeValue);
                        ClassUtils.setFieldValue(result, field, val);
                    }
                });
    }
    
}
