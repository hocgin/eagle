package in.hocg.eagle.modules.crm.pojo.qo.comment;

import in.hocg.eagle.basic.constant.datadict.CommentTargetType;
import in.hocg.eagle.basic.pojo.qo.PageQo;
import in.hocg.eagle.basic.valid.RangeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RootCommentPagingQo extends PageQo {
    /**
     * @see CommentTargetType 的名称
     */
    @ApiModelProperty("评论对象")
    @RangeEnum(enumClass = CommentTargetType.class, message = "评论对象类型")
    private Integer refType;
    @ApiModelProperty("评论对象的原ID,如:文章ID")
    private Long refId;
}
