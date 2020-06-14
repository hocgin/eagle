package in.hocg.eagle.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.web.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 微信公众号配置表
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_mp_config")
public class WxMpConfig extends AbstractEntity<WxMpConfig> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("开发者ID(AppID)")
    @TableId(value = "appid", type = IdType.INPUT)
    private String appid;
    @ApiModelProperty("微信公众号标题")
    @TableField("title")
    private String title;
    @ApiModelProperty("开发者密码(AppSecret)")
    @TableField("app_secret")
    private String appSecret;
    @ApiModelProperty("令牌(Token)")
    @TableField("token")
    private String token;
    @ApiModelProperty("消息加解密密钥(EncodingAESKey)")
    @TableField("aes_key")
    private String aesKey;
    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    @TableField("enabled")
    private Integer enabled;
    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建者")
    @TableField("creator")
    private Long creator;
    @ApiModelProperty("更新时间")
    @TableField("last_updated_at")
    private LocalDateTime lastUpdatedAt;
    @ApiModelProperty("更新者")
    @TableField("last_updater")
    private Long lastUpdater;


    @Override
    public Serializable pkVal() {
        return this.appid;
    }

}
