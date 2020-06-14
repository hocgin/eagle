package in.hocg.eagle.modules.crm.pojo.qo.comment;

import in.hocg.web.constant.datadict.CommentTargetType;
import in.hocg.web.pojo.qo.BaseQo;
import in.hocg.web.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentPostQo extends BaseQo {
    /**
     * @see CommentTargetType 的名称
     */
    @NotNull
    @EnumRange(enumClass = CommentTargetType.class, message = "评论对象类型")
    @ApiModelProperty("评论对象")
    private Integer refType;
    @NotNull
    @ApiModelProperty("评论对象的原ID,如:文章ID")
    private Long refId;
    @ApiModelProperty("评论父级ID")
    private Long parentId;
    @NotEmpty(message = "评论内容不能为空")
    @ApiModelProperty(value = "评论内容", required = true)
    private String content;
}
