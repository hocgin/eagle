package in.hocg.eagle.basic;

import in.hocg.eagle.basic.aspect.logger.DefaultLoggerService;
import in.hocg.eagle.basic.aspect.logger.LoggerService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hocgin on 2020/2/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Configuration
public class BeanStarter {
    
    @Bean
    public SnowFlake snowFlake() {
        return new SnowFlake(1, 1);
    }
    
    @Bean
    @ConditionalOnMissingBean(LoggerService.class)
    public LoggerService loggerService() {
        return new DefaultLoggerService();
    }
}
