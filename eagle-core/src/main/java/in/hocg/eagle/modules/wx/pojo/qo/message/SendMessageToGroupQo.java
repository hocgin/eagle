package in.hocg.eagle.modules.wx.pojo.qo.message;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import in.hocg.eagle.basic.valid.StringRange;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.common.api.WxConsts;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SendMessageToGroupQo extends BaseRo {
    @NotBlank(message = "APP ID 不能为空")
    private String appid;
    @NotNull(message = "标签不能为空")
    private Long tagId;

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
