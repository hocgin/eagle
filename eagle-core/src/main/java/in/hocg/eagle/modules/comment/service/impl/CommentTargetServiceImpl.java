package in.hocg.eagle.modules.comment.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.CommentTargetType;
import in.hocg.eagle.modules.comment.entity.CommentTarget;
import in.hocg.eagle.modules.comment.mapper.CommentTargetMapper;
import in.hocg.eagle.modules.comment.service.CommentTargetService;
import in.hocg.eagle.utils.VerifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <p>
 * [评论模块] 评论对象表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-08
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CommentTargetServiceImpl extends AbstractServiceImpl<CommentTargetMapper, CommentTarget> implements CommentTargetService {

    /**
     * 获取或创建评论对象ID
     *
     * @param relType
     * @param relId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long getOrCreateCommentTarget(CommentTargetType relType, Long relId) {
        final Optional<Long> idOpt = getCommentTarget(relType, relId);
        return idOpt.orElseGet(() -> insertOne(new CommentTarget()
            .setRelId(relId)
            .setRelType(relType.getCode())));
    }

    /**
     * 获取评论对象ID
     *
     * @param relType
     * @param relId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Long> getCommentTarget(CommentTargetType relType, Long relId) {
        VerifyUtils.notNull(relType);
        VerifyUtils.notNull(relId);
        final Integer relTypeCode = relType.getCode();
        return selectOneByRelTypeAndRelId(relTypeCode, relId);
    }

    private Optional<Long> selectOneByRelTypeAndRelId(Integer relType, Long relId) {
        return lambdaQuery().eq(CommentTarget::getRelType, relType)
            .eq(CommentTarget::getRelId, relId)
            .oneOpt()
            .map(CommentTarget::getId);
    }

    private Long insertOne(CommentTarget entity) {
        verifyEntity(entity);
        baseMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void verifyEntity(CommentTarget entity) {

    }
}
