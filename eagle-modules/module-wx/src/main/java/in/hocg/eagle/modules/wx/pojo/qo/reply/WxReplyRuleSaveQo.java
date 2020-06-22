package in.hocg.eagle.modules.wx.pojo.qo.reply;

import in.hocg.web.constant.datadict.WxMatchMsgType;
import in.hocg.web.constant.datadict.WxReplyMsgType;
import in.hocg.web.pojo.qo.BaseQo;
import in.hocg.web.pojo.qo.Insert;
import in.hocg.web.valid.EnumRange;
import in.hocg.web.utils.string.JsonUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/5/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxReplyRuleSaveQo extends BaseQo {
    private Long id;

    @NotBlank(groups = {Insert.class}, message = "APPID 不能为空")
    @ApiModelProperty("开发者ID(AppID)")
    private String appid;
    @NotBlank(groups = {Insert.class}, message = "规则名称不能为空")
    @ApiModelProperty("规则名称")
    private String title;
    @NotNull(groups = {Insert.class}, message = "启用状态不能为空")
    @ApiModelProperty("启用状态(0:为禁用状态;1:为正常状态)")
    private Integer enabled;
    @NotNull(groups = {Insert.class}, message = "匹配消息类型不能为空")
    @EnumRange(enumClass = WxMatchMsgType.class, groups = {Insert.class}, message = "匹配消息类型不能为空")
    @ApiModelProperty("匹配消息类型")
    private Integer matchMsgType;
    @NotNull(groups = {Insert.class}, message = "匹配规则不能为空")
    @ApiModelProperty("匹配规则")
    private Object matchRule;
    @NotNull(groups = {Insert.class}, message = "回复消息类型不能为空")
    @EnumRange(enumClass = WxReplyMsgType.class, groups = {Insert.class}, message = "回复消息类型错误")
    @ApiModelProperty("回复消息类型")
    private Integer replyMsgType;
    @NotNull(groups = {Insert.class}, message = "回复内容不能为空")
    @ApiModelProperty("回复内容")
    private Object replyContent;
    @ApiModelProperty("排序, 从大到小降序")
    private Integer sort;

    public String getMatchRuleString() {
        return JsonUtils.toJSONString(matchRule);
    }

    public String getReplyContentString() {
        return JsonUtils.toJSONString(replyContent);
    }
}
