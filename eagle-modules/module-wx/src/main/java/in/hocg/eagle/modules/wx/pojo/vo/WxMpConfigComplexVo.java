package in.hocg.eagle.modules.wx.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.web.aspect.named.InjectNamed;
import in.hocg.web.aspect.named.Named;
import in.hocg.web.aspect.named.NamedType;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.jackson.LocalDateTimeSerializer;
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
@InjectNamed
public class WxMpConfigComplexVo {
    @ApiModelProperty("开发者ID(AppID)")
    private String appid;
    @ApiModelProperty("微信公众号标题")
    private String title;
    @ApiModelProperty("开发者密码(AppSecret)")
    private String appSecret;
    @ApiModelProperty("令牌(Token)")
    private String token;
    @ApiModelProperty("消息加解密密钥(EncodingAESKey)")
    private String aesKey;

    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    private Integer enabled;
    @Named(idFor = "enabled", type = NamedType.DataDict, args = {Enabled.KEY})
    private String enabledName;
    private Long creator;
    @Named(idFor = "creator", type = NamedType.Nickname)
    private String creatorName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    private Long lastUpdater;
    @ApiModelProperty("更新者")
    @Named(idFor = "lastUpdater", type = NamedType.Nickname)
    private String lastUpdaterName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
}
