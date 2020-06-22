package in.hocg.eagle.modules.crm.pojo.qo.comment;

import in.hocg.web.pojo.qo.PageQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChildCommentPagingQo extends PageQo {
    @ApiModelProperty("父级评论ID")
    private Long parentId;
}
