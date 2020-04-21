package in.hocg.eagle.basic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.boot.context.properties.bind.Binder;
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
    private final Configs configs;

    static {
        ENVIRONMENT = SpringContext.getBean(Environment.class);
        configs = Binder.get(ENVIRONMENT).bind(Configs.PREFIX, Configs.class).orElse(new Configs());
    }

    @Data
    public class Configs {
        public static final String PREFIX = "eagle";
        @ApiModelProperty("服务域名")
        private String hostname = "http://127.0.0.1:8080";
        @ApiModelProperty("amap")
        private String amap;
        @ApiModelProperty("ip138")
        private String ip138;

        @ApiModelProperty("微信 APP ID")
        private String wxAppId;
        @ApiModelProperty("微信 KEY")
        private String wxKey;
        @ApiModelProperty("微信商户号")
        private String wxMchId;
        @ApiModelProperty("微信证书绝对路径")
        private String wxCertFile;

        @ApiModelProperty("支付宝 公钥")
        private String aliPayPublicKey;
        @ApiModelProperty("支付宝 私钥")
        private String aliPayPrivateKey;
        @ApiModelProperty("支付宝 APPID")
        private String aliPayAppId;

        @ApiModelProperty("微信公众号 APPID")
        private String wxMpAppId;
        @ApiModelProperty("微信公众号 Secret")
        private String wxMpSecret;
        @ApiModelProperty("微信公众号 Token")
        private String wxMpToken;
        @ApiModelProperty("微信公众号 AesKey")
        private String wxMpAesKey;
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
    public static Configs getConfigs() {
        return configs;
    }

    /**
     * 获取当前服务器的域名
     *
     * @return
     */
    public static String hostname() {
        return getConfigs().getHostname();
    }
}
