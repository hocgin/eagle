package in.hocg.eagle.basic.aspect.logger;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.security.SecurityContext;
import in.hocg.eagle.basic.security.User;
import in.hocg.eagle.utils.DateUtils;
import in.hocg.eagle.utils.JSONUtility;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * @author hocgin
 * @date 2019/6/25
 */
@Slf4j
@Aspect
@Order(Integer.MIN_VALUE)
@Component
public class LoggerAspect {
    
    @Pointcut("execution(public * *.*(..)) && @annotation(in.hocg.eagle.basic.aspect.logger.UseLogger)")
    public void pointcut() {
    }
    
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 耗时统计
        StopWatch watch = new StopWatch();
        watch.start();
        Object ret = null;
        try {
            ret = point.proceed();
        } catch (Exception e) {
            log.warn("发生异常:", e);
            throw e;
        } finally {
            watch.stop();
            // 日志收集
            Object target = point.getTarget();
            MethodSignature signature = (MethodSignature) point.getSignature();
            String methodName = signature.getName();
            String[] parameterNames = signature.getParameterNames();
            Class aClass = point.getSourceLocation().getWithinType();
            String mapping = String.format("%s#%s(%s)", aClass.getName(), methodName, Arrays.toString(parameterNames));
            Class[] parameterTypes = signature.getMethod().getParameterTypes();
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            UseLogger annotation = method.getAnnotation(UseLogger.class);
            Object[] args = point.getArgs();
            
            HttpServletRequest request = SpringContext.getRequest();
            String uri = request.getRequestURI();
            String requestMethod = request.getMethod();
            
            StringJoiner stringJoiner = new StringJoiner("\n")
                    .add("")
                    .add("╔═[{}]═{}════════════════════════════════════════════════")
                    .add("║ {}")
                    .add("║ > {} ({})")
                    .add("╠═[请求体]════════════════════════════════════════════════════════════════════════════")
                    .add("║ {}")
                    .add("╠═[响应体]════════════════════════════════════════════════════════════════════════════")
                    .add("║ {}")
                    .add("╚═[总耗时:{} ms]══════════════════════════════════════════════════════════════════════")
                    .add("");
            log.info(stringJoiner.toString(),
                    DateUtils.format(LocalDateTime.now(), DateUtils.SIMPLE_FORMATTER),
                    getUserString(),
                    String.format("%s %s", requestMethod, uri),
                    annotation.value(),
                    mapping,
                    JSONUtility.toJSONString(args, true).replaceAll("\n", "\n║ "),
                    JSONUtility.toJSONString(ret, true).replaceAll("\n", "\n║ "),
                    watch.getTotalTimeMillis());
        }
        
        return ret;
    }
    
    private String getUserString() {
        final Optional<User> currentUser = SecurityContext.getCurrentUser();
        if (currentUser.isPresent()) {
            final User user = currentUser.get();
            return String.format("[@%s(ID:%s)]", user.getUsername(), user.getId());
        }
        return "";
    }
}
