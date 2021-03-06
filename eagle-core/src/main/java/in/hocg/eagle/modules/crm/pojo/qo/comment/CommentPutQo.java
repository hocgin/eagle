package in.hocg.eagle.modules.crm.pojo.qo.comment;

import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.basic.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentPutQo extends IdRo {
    @NotEmpty(message = "评论内容不能为空")
    @ApiModelProperty(value = "评论内容", required = true)
    private String content;

    @ApiModelProperty("启用状态")
    @EnumRange(enumClass = Enabled.class)
    private Integer enabled;
}
