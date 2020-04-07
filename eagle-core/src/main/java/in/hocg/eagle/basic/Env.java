package in.hocg.eagle.basic;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.UtilityClass;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by hocgin on 2019/5/26.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class Env {
    private final Map<String, String> DEFAULT_VALUES = Maps.newHashMap();
    private final String PREFIX = "eagle";
    private final Environment ENVIRONMENT;

    static {
        ENVIRONMENT = SpringContext.getBean(Environment.class);
        DEFAULT_VALUES.put(Keys.HOSTNAME, "http://127.0.0.1:8080");
    }

    @ApiModel("配置KEY")
    static class Keys {
        @ApiModelProperty("服务域名")
        public static final String HOSTNAME = PREFIX + ".hostname";
    }

    /**
     * 当前是否为开发环境
     *
     * @return
     */
    public static boolean isDev() {
        String[] profiles = ENVIRONMENT.getActiveProfiles();
        return Arrays.asList(profiles).contains("dev");
    }

    /**
     * 获取当前服务器的域名
     *
     * @return
     */
    public static String hostname() {
        return get(Keys.HOSTNAME);
    }

    private static <T> T get(String key) {
        return ((T) ENVIRONMENT.getProperty(key, DEFAULT_VALUES.get(key)));
    }
}
