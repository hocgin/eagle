package in.hocg.eagle.modules.mms.pojo.qo.notify;

import com.google.common.collect.Lists;
import in.hocg.web.pojo.qo.BaseQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hocgin on 2020/3/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PublishPrivateLetterQo extends BaseQo {
    @NotBlank(message = "消息文本不能为空")
    @ApiModelProperty("消息文本")
    private String content;
    @ApiModelProperty("触发者ID")
    private Long actorId;

    @NotNull(message = "请选择接收人")
    @Size(min = 1, message = "请选择接收人")
    @ApiModelProperty("接收人IDs")
    private List<Long> receivers = Lists.newArrayList();

}
