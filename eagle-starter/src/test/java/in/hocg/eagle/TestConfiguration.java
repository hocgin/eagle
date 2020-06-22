package in.hocg.eagle;

import in.hocg.AppApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackageClasses = AppApplication.class)
@EnableAutoConfiguration
public class TestConfiguration {
}
