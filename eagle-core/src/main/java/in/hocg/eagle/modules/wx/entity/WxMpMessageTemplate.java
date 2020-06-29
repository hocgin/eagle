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
import java.time.LocalDateTime;

/**
 * <p>
 * [微信模块] 消息模版表
 * </p>
 *
 * @author hocgin
 * @since 2020-05-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_mp_message_template")
public class WxMpMessageTemplate extends AbstractEntity<WxMpMessageTemplate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("开发者ID(AppID)")
    @TableField("appid")
    private String appid;
    @ApiModelProperty("模版ID")
    @TableField("template_id")
    private String templateId;
    @ApiModelProperty("模版标题")
    @TableField("title")
    private String title;
    @ApiModelProperty("模板所属行业的一级行业")
    @TableField("primary_industry")
    private String primaryIndustry;
    @ApiModelProperty("模板所属行业的二级行业")
    @TableField("deputy_industry")
    private String deputyIndustry;
    @ApiModelProperty("模板内容")
    @TableField("content")
    private String content;
    @ApiModelProperty("模板示例")
    @TableField("example")
    private String example;

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
        return this.id;
    }

}
