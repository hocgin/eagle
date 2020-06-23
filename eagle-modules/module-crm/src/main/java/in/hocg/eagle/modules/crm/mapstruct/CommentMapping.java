package in.hocg.eagle.modules.crm.mapstruct;

import in.hocg.eagle.modules.crm.pojo.qo.comment.CommentPostQo;
import in.hocg.eagle.modules.crm.pojo.qo.comment.CommentPutQo;
import in.hocg.basic.api.vo.CommentComplexVo;
import in.hocg.eagle.modules.crm.pojo.vo.comment.RootCommentComplexVo;
import in.hocg.eagle.modules.crm.entity.Comment;
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
    CommentComplexVo asCommentComplexVo(Comment entity);

    @Mapping(target = "childrenTotal", ignore = true)
    RootCommentComplexVo asRootCommentComplexVo(CommentComplexVo entity);

    @Mapping(target = "targetId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "treePath", ignore = true)
    Comment asComment(CommentPostQo qo);

    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "targetId", ignore = true)
    @Mapping(target = "treePath", ignore = true)
    Comment asComment(CommentPutQo qo);
}
