package in.hocg.eagle;

import in.hocg.eagle.aspect.DefaultLoggerService;
import in.hocg.web.SnowFlake;
import in.hocg.web.aspect.logger.LoggerService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
