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
 * [微信模块] 微信素材库
 * </p>
 *
 * @author hocgin
 * @since 2020-05-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_material")
public class WxMaterial extends AbstractEntity<WxMaterial> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("APP ID")
    @TableField("appid")
    private String appid;
    @ApiModelProperty("素材类型, 0->图片; 1->语音; 2->视频; 3->缩略图; 4->图文")
    @TableField("material_type")
    private Integer materialType;
    @ApiModelProperty("素材上传后获得")
    @TableField("material_result")
    private String materialResult;
    @ApiModelProperty("素材内容")
    @TableField("material_content")
    private String materialContent;

    @TableField("creator")
    private Long creator;
    @TableField("created_at")
    private LocalDateTime createdAt;
    @TableField("last_updater")
    private Long lastUpdater;
    @TableField("last_updated_at")
    private LocalDateTime lastUpdatedAt;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
