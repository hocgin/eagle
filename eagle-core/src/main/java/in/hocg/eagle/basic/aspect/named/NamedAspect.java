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
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author hocgin
 * @date 2017/11/17
 * email: hocgin@gmail.com
 * <p>
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class NamedAspect {
    private final NamedService namedService;
    
    @Pointcut("@annotation(in.hocg.eagle.basic.aspect.named.InjectNamed)")
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
        this.handleCollectionResult(result.getRecords());
    }
    
    private void handleArrayResult(Object[] result) {
        for (Object item : result) {
            handleObjectResult(item);
        }
    }
    
    private void handleCollectionResult(Collection result) {
        result.forEach(this::handleObjectResult);
    }
    
    private void handleObjectResult(Object result) {
        if (Objects.isNull(result)) {
            return;
        }
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
                    if (Objects.nonNull(idField)) {
                        String codeValue = ClassUtils.getObjectValue(result, idField, null);
                        Object val = namedService.selectOneByTypeAndId(named.type(), codeValue);
                        ClassUtils.setFieldValue(result, field, val);
                    }
                });
    }
    
}
