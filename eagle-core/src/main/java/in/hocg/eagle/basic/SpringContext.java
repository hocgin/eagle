package in.hocg.eagle.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hocgin
 * @date 2019/7/19
 */
@Slf4j
@Component
public class SpringContext implements ApplicationContextAware {
    
    private static ApplicationContext APPLICATION_CONTEXT;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContext.APPLICATION_CONTEXT = applicationContext;
    }
    
    /**
     * 获取上下文
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }
    
    
    /**
     * 通过名字获取上下文中的bean
     *
     * @param name
     * @return
     */
    public static <T> T getBean(String name) {
        return ((T) APPLICATION_CONTEXT.getBean(name));
    }
    
    /**
     * 通过类型获取上下文中的bean
     *
     * @param requiredType
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return APPLICATION_CONTEXT.getBean(requiredType);
    }
    
    public static HttpServletResponse getResponse() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }
    
    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }
}
