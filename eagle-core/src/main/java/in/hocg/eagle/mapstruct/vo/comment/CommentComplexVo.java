package in.hocg.eagle.mapstruct.vo.comment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
import in.hocg.eagle.mapstruct.vo.account.AccountComplexVo;
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
    @ApiModelProperty("t_comment::id")
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