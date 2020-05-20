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

/**
 * <p>
 * [微信模块] 用户标签表
 * </p>
 *
 * @author hocgin
 * @since 2020-05-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_mp_user_tags")
public class WxMpUserTags extends AbstractEntity<WxMpUserTags> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("开发者ID(AppID)")
    @TableField("appid")
    private String appid;
    @ApiModelProperty("标签ID(来着微信)")
    @TableField("tag_id")
    private Long tagId;
    @ApiModelProperty("标签名称(来着微信)")
    @TableField("name")
    private String name;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
