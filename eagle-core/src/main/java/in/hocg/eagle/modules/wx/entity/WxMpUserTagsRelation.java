package in.hocg.eagle.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * [微信模块] 标签x用户表
 * </p>
 *
 * @author hocgin
 * @since 2020-05-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_mp_user_tags_relation")
public class WxMpUserTagsRelation extends AbstractEntity<WxMpUserTagsRelation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("用户标签ID(来着自己)")
    @TableField("tags_id")
    private Long tagsId;
    @ApiModelProperty("用户的标识，对当前公众号唯一")
    @TableField("openid")
    private String openid;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
