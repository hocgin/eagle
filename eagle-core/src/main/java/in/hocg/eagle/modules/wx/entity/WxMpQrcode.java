package in.hocg.eagle.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * [微信模块] 二维码表
 * </p>
 *
 * @author hocgin
 * @since 2020-05-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_mp_qrcode")
public class WxMpQrcode extends AbstractEntity<WxMpQrcode> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("开发者ID(AppID)")
    @TableField("appid")
    private String appid;
    @ApiModelProperty("过期时间(秒), -1 为永久型")
    @TableField("expire_seconds")
    private Integer expireSeconds;
    @ApiModelProperty("场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000")
    @TableField("scene_id")
    private Integer sceneId;
    @ApiModelProperty("场景值ID（字符串形式的ID），字符串类型，长度限制为1到64")
    @TableField("scene_str")
    private String sceneStr;
    @ApiModelProperty("获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码")
    @TableField("ticket")
    private String ticket;
    @ApiModelProperty("二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片")
    @TableField("url")
    private String url;
    @TableField("expire_at")
    private LocalDateTime expireAt;
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
