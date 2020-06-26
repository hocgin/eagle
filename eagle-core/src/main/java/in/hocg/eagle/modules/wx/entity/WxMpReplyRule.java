package in.hocg.eagle.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 微信自动回复配置表
 * </p>
 *
 * @author hocgin
 * @since 2020-05-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_mp_reply_rule")
public class WxMpReplyRule extends AbstractEntity<WxMpReplyRule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("开发者ID(AppID)")
    @TableField("appid")
    private String appid;
    @ApiModelProperty("规则名称")
    @TableField("title")
    private String title;
    @ApiModelProperty("启用状态(0:为禁用状态;1:为正常状态)")
    @TableField("enabled")
    private Integer enabled;
    @ApiModelProperty("匹配消息类型")
    @TableField("match_msg_type")
    private Integer matchMsgType;
    @ApiModelProperty("匹配规则")
    @TableField("match_rule")
    private String matchRule;
    @ApiModelProperty("回复消息类型")
    @TableField("reply_msg_type")
    private Integer replyMsgType;
    @ApiModelProperty("回复内容")
    @TableField("reply_content")
    private String replyContent;
    @ApiModelProperty("排序, 从大到小降序")
    @TableField("sort")
    private Integer sort;
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
