package in.hocg.eagle.basic.env;

import in.hocg.eagle.basic.SpringContext;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * Created by hocgin on 2019/5/26.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@UtilityClass
public class Env {
    private Environment environment;
    private EnvConfigs configs;

    /**
     * 当前是否为开发环境
     *
     * @return
     */
    public boolean isDev() {
        String[] profiles = getActiveProfiles();
        return Arrays.asList(profiles).contains("dev");
    }

    public String[] getActiveProfiles() {
        return getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前服务器的域名
     *
     * @return
     */
    public String hostname() {
        return getConfigs().getHostname();
    }

    /**
     * 配置
     *
     * @return
     */
    public EnvConfigs getConfigs() {
        if (configs == null) {
            lazyLoad();
        }
        return configs;
    }

    private Environment getEnvironment() {
        if (environment == null) {
            lazyLoad();
        }
        return environment;
    }

    private void lazyLoad() {
        environment = SpringContext.getBean(Environment.class);
        configs = SpringContext.getBean(EnvConfigs.class);
    }
}
