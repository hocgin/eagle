package in.hocg.eagle.modules.comment.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.constant.datadict.CommentTargetType;
import in.hocg.eagle.modules.comment.entity.CommentTarget;

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

    /**
     * 获取或创建评论对象ID
     *
     * @param relType
     * @param relId
     * @return
     */
    Long getOrCreateCommentTarget(CommentTargetType relType, Long relId);

    /**
     * 获取评论对象ID
     *
     * @param relType
     * @param relId
     * @return
     */
    Optional<Long> getCommentTarget(CommentTargetType relType, Long relId);
}
