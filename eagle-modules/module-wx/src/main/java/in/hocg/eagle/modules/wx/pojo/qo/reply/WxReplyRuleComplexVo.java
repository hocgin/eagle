package in.hocg.eagle.modules.wx.pojo.qo.reply;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.basic.named.InjectNamed;
import in.hocg.basic.named.Named;
import in.hocg.basic.named.NamedType;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.constant.datadict.WxMatchMsgType;
import in.hocg.web.constant.datadict.WxReplyMsgType;
import in.hocg.web.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class WxReplyRuleComplexVo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("开发者ID(AppID)")
    private String appid;
    @ApiModelProperty("规则名称")
    private String title;
    @ApiModelProperty("启用状态(0:为禁用状态;1:为正常状态)")
    private Integer enabled;
    @Named(idFor = "enabled", type = NamedType.DataDict, args = {Enabled.KEY})
    private String enabledName;
    @ApiModelProperty("匹配消息类型")
    private Integer matchMsgType;
    @Named(idFor = "matchMsgType", type = NamedType.DataDict, args = {WxMatchMsgType.KEY})
    private String matchMsgTypeName;
    @ApiModelProperty("匹配规则")
    private Object matchRule;
    @ApiModelProperty("回复消息类型")
    private Integer replyMsgType;
    @Named(idFor = "replyMsgType", type = NamedType.DataDict, args = {WxReplyMsgType.KEY})
    private String replyMsgTypeName;
    @ApiModelProperty("回复内容")
    private Object replyContent;
    @ApiModelProperty("排序, 从大到小降序")
    private Integer sort;

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
