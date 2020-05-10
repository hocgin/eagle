package in.hocg.eagle.basic.env;

import in.hocg.eagle.basic.SpringContext;
import lombok.experimental.UtilityClass;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * Created by hocgin on 2019/5/26.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class Env {
    private final Environment ENVIRONMENT;
    private final EnvConfigs configs;

    static {
        ENVIRONMENT = SpringContext.getBean(Environment.class);
        configs = SpringContext.getBean(EnvConfigs.class);
//        configs = Binder.get(ENVIRONMENT).bind(Configs.PREFIX, Configs.class).orElse(new Configs());
    }

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
        return ENVIRONMENT.getActiveProfiles();
    }

    /**
     * 配置
     *
     * @return
     */
    public EnvConfigs getConfigs() {
        return configs;
    }

    /**
     * 获取当前服务器的域名
     *
     * @return
     */
    public String hostname() {
        return getConfigs().getHostname();
    }
}
