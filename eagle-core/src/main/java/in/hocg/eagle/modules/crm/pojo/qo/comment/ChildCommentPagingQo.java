package in.hocg.eagle.modules.crm.pojo.qo.comment;

import in.hocg.eagle.basic.pojo.ro.PageRo;
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
public class ChildCommentPagingQo extends PageRo {
    @ApiModelProperty("父级评论ID")
    private Long parentId;
}
