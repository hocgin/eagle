package in.hocg.eagle.modules.wx.pojo.qo.reply;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.pojo.qo.Insert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(groups = {Insert.class}, message = "启用状态不能为空")
    @ApiModelProperty("启用状态(0:为禁用状态;1:为正常状态)")
    private Integer enabled;
    @NotBlank(groups = {Insert.class}, message = "匹配消息类型不能为空")
    @ApiModelProperty("匹配消息类型")
    private Integer matchMsgType;
    @NotBlank(groups = {Insert.class}, message = "匹配规则不能为空")
    @ApiModelProperty("匹配规则")
    private String matchRule;
    @NotBlank(groups = {Insert.class}, message = "回复消息类型不能为空")
    @ApiModelProperty("回复消息类型")
    private Integer replyMsgType;
    @NotBlank(groups = {Insert.class}, message = "回复内容不能为空")
    @ApiModelProperty("回复内容")
    private String replyContent;
    @ApiModelProperty("排序, 从大到小降序")
    private Integer sort;

}
