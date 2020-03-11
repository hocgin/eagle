package in.hocg.eagle.modules.comment.pojo.qo.comment;

import in.hocg.eagle.basic.constant.datadict.CommentTargetType;
import in.hocg.eagle.basic.pojo.qo.PageQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class RootCommentPagingQo extends PageQo {
    /**
     * @see CommentTargetType 的名称
     */
    @ApiModelProperty("评论对象")
    private String targetTypeCode;
    @ApiModelProperty("评论对象的原ID,如:文章ID")
    private Long id;
}
