package in.hocg.eagle.modules.wx.pojo.qo.message;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class SendMessageToGroupQo extends BaseQo {
    @NotBlank(message = "APP ID 不能为空")
    private String appid;
    @NotNull(message = "标签不能为空")
    private Long tagId;
    @NotBlank(message = "消息类型不能为空")
    private String msgType;
    private String content;
    private String mediaId;
}
