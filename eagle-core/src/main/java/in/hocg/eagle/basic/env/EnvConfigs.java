package in.hocg.eagle.basic.env;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by hocgin on 2020/4/26.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Component
@ConfigurationProperties(prefix = EnvConfigs.PREFIX)
public class EnvConfigs {
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
