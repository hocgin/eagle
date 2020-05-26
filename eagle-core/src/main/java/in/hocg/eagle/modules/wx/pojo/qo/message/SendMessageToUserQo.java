package in.hocg.eagle.modules.wx.pojo.qo.message;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.valid.StringRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.common.api.WxConsts;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SendMessageToUserQo extends BaseQo {
    @ApiModelProperty("微信用户ID")
    @Size(min = 1, message = "接收人不能为空")
    private List<Long> toUsers;

    @StringRange(strings = {
        WxConsts.MassMsgType.VOICE,
        WxConsts.MassMsgType.MPVIDEO,
        WxConsts.MassMsgType.IMAGE,
        WxConsts.MassMsgType.MPNEWS,
        WxConsts.MassMsgType.TEXT}, message = "消息类型错误")
    @NotBlank(message = "消息类型不能为空")
    private String msgType;
    private String content;
    private String mediaId;

}
