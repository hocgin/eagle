package in.hocg.eagle.mapstruct;

import in.hocg.eagle.mapstruct.qo.comment.CommentPostQo;
import in.hocg.eagle.mapstruct.vo.comment.CommentComplexVo;
import in.hocg.eagle.mapstruct.vo.comment.RootCommentComplexVo;
import in.hocg.eagle.modules.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface CommentMapping {

    @Mapping(target = "PCommenter", ignore = true)
    @Mapping(target = "PCommenterId", ignore = true)
    @Mapping(target = "commenterId", source = "entity.creator")
    @Mapping(target = "commenter", ignore = true)
    @Mapping(target = "pCommenterId", ignore = true)
    @Mapping(target = "pCommenter", ignore = true)
    CommentComplexVo asCommentComplexVo(Comment entity);

    @Mapping(target = "childrenTotal", ignore = true)
    @Mapping(target = "PCommenter", ignore = true)
    @Mapping(target = "PCommenterId", ignore = true)
    @Mapping(target = "commenterId", source = "entity.creator")
    @Mapping(target = "commenter", ignore = true)
    @Mapping(target = "pCommenterId", ignore = true)
    @Mapping(target = "pCommenter", ignore = true)
    RootCommentComplexVo asRootCommentComplexVo(Comment entity);

    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "targetId", ignore = true)
    @Mapping(target = "treePath", ignore = true)
    Comment asComment(CommentPostQo qo);
}
