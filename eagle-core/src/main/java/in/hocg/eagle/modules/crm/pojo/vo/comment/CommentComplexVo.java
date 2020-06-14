package in.hocg.eagle.modules.crm.pojo.vo.comment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.web.jackson.LocalDateTimeSerializer;
import in.hocg.eagle.modules.ums.pojo.vo.account.AccountComplexVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class CommentComplexVo {
    @ApiModelProperty("crm_comment::id")
    private Long id;
    @ApiModelProperty("评论内容")
    private String content;
    @ApiModelProperty("评论者")
    private Long commenterId;
    private AccountComplexVo commenter;
    @ApiModelProperty("被评论者")
    private Long pCommenterId;
    private AccountComplexVo pCommenter;
    @ApiModelProperty("评论时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
}
