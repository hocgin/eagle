package in.hocg.eagle.modules.wx.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/4/26.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class WxMpConfigComplexVo {
    @ApiModelProperty("开发者ID(AppID)")
    private String appid;
    @ApiModelProperty("开发者密码(AppSecret)")
    private String appSecret;
    @ApiModelProperty("令牌(Token)")
    private String token;
    @ApiModelProperty("消息加解密密钥(EncodingAESKey)")
    private String aesKey;
    @ApiModelProperty("启用状态")
    private Integer enabled;
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建者")
    private Long creator;
    @ApiModelProperty("更新时间")
    private LocalDateTime lastUpdatedAt;
    @ApiModelProperty("更新者")
    private Long lastUpdater;
}
