package in.hocg.eagle.basic.aspect.named;

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
    
    @Pointcut("execution(* *(..))")
    public void pointcut() {
    }
    
    @Around("pointcut()")
    public Object logHandler(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        if (Objects.isNull(result)) {
            return null;
        }
        final Class<?> aClass = result.getClass();
        
        Map<String, Field> fieldMap = LangUtils.toMap(ClassUtils.getAllField(aClass), Field::getName);
        fieldMap.values().stream()
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
        return result;
    }
    
    
}
