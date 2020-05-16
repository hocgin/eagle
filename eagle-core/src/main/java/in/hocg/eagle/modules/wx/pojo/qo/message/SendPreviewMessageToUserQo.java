package in.hocg.eagle.modules.wx.pojo.qo.message;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SendPreviewMessageToUserQo extends BaseQo {
    @ApiModelProperty("微信用户ID")
    @Length(min = 1, message = "接收人不能为空")
    private List<Long> toUsers;
    @NotBlank(message = "消息类型不能为空")
    private String msgType;
    private String content;
    private String mediaId;
}
