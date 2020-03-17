package in.hocg.eagle.modules.crm.pojo.vo.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class RootCommentComplexVo extends CommentComplexVo {
    @ApiModelProperty(value = "子评论条数")
    private Integer childrenTotal = 0;
}
