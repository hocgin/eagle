package in.hocg.eagle.mapstruct.qo.comment;

import in.hocg.eagle.basic.pojo.qo.PageQo;
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
public class G2ndAfterCommentPagingQo extends PageQo {
    @ApiModelProperty("父级评论ID")
    private Long parentId;
}
