package in.hocg.eagle.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * [微信模块] 短链接表
 * </p>
 *
 * @author hocgin
 * @since 2020-05-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_mp_short_url")
public class WxMpShortUrl extends AbstractEntity<WxMpShortUrl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("开发者ID(AppID)")
    @TableField("appid")
    private String appid;
    @ApiModelProperty("长链接")
    @TableField("long_url")
    private String longUrl;
    @ApiModelProperty("短链接")
    @TableField("short_url")
    private String shortUrl;
    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建者")
    @TableField("creator")
    private Long creator;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
