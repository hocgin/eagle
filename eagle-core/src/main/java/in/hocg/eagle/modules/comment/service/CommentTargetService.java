package in.hocg.eagle.modules.comment.service;

import in.hocg.eagle.basic.constant.datadict.CommentTargetType;
import in.hocg.eagle.modules.comment.entity.CommentTarget;
import in.hocg.eagle.basic.AbstractService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <p>
 * [评论模块] 评论对象表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-08
 */
public interface CommentTargetService extends AbstractService<CommentTarget> {

    @Transactional(rollbackFor = Exception.class)
    Long getOrCreateCommentTarget(CommentTargetType relType, Long relId);

    @Transactional(rollbackFor = Exception.class)
    Optional<Long> getCommentTarget(CommentTargetType relType, Long relId);
}
