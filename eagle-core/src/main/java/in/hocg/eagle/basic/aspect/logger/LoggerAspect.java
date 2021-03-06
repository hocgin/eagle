package in.hocg.eagle.basic.aspect.logger;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.ext.security.SecurityContext;
import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.utils.web.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        String errorMessage = null;
        try {
            ret = point.proceed();
        } catch (Exception e) {
            log.warn("发生异常:", e);
            errorMessage = e.getMessage();
            throw e;
        } finally {
            watch.stop();
            // 日志收集
            Object target = point.getTarget();
            MethodSignature signature = (MethodSignature) point.getSignature();
            String methodName = signature.getName();
            String[] parameterNames = signature.getParameterNames();
            Class<?> aClass = point.getSourceLocation().getWithinType();
            String mapping = String.format("%s#%s(%s)", aClass.getName(), methodName, Arrays.toString(parameterNames));
            Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            UseLogger annotation = method.getAnnotation(UseLogger.class);
            List<Object> args = Lists.newArrayList(point.getArgs())
                .parallelStream()
                .filter(arg -> (!(arg instanceof ServletRequest)
                    && !(arg instanceof ServletResponse)
                    && !(arg instanceof InputStreamSource)
                    && !(arg instanceof InputStream))
                    && !(arg instanceof OutputStream))
                .collect(Collectors.toList());

            Optional<HttpServletRequest> requestOpt = SpringContext.getRequest();
            String uri = "Unknown";
            String requestMethod = "Unknown";
            String host = "Unknown";
            String userAgent = "Unknown";
            String clientIp = "0.0.0.0";
            String source = null;
            if (requestOpt.isPresent()) {
                final HttpServletRequest request = requestOpt.get();
                uri = request.getRequestURI();
                requestMethod = request.getMethod();
                userAgent = RequestUtils.getUserAgent(request);
                host = RequestUtils.getHost(request);
                clientIp = RequestUtils.getClientIP(request);
                source = request.getParameter("source");
            }
            final Logger logger = new Logger();
            final String enterRemark = annotation.value();
            logger.setCurrentUser(SecurityContext.getCurrentUser().orElse(null))
                .setMapping(mapping)
                .setSource(source)
                .setException(errorMessage)
                .setEnterRemark(enterRemark)
                .setHost(host)
                .setCreatedAt(LocalDateTime.now())
                .setClientIp(clientIp)
                .setUserAgent(userAgent)
                .setMethod(requestMethod)
                .setTotalTimeMillis(watch.getTotalTimeMillis())
                .setUri(uri)
                .setRet(ret)
                .setArgs(args);
            logger.save();
        }

        return ret;
    }

}
