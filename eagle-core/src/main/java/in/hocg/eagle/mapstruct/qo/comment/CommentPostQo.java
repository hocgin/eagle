package in.hocg.eagle.mapstruct.qo.comment;

import in.hocg.eagle.basic.constant.datadict.CommentTargetType;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
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
public class CommentPostQo extends BaseQo {
    /**
     * @see CommentTargetType 的名称
     */
    @ApiModelProperty("评论对象")
    private String targetTypeCode;
    @ApiModelProperty("评论对象的原ID,如:文章ID")
    private Long id;
    @ApiModelProperty("评论父级ID")
    private Long parentId;
    @NotEmpty(message = "评论内容不能为空")
    @ApiModelProperty(value = "评论内容", required = true)
    private String content;
}
