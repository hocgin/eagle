package in.hocg.eagle.modules.crm.service.impl;

import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.CommentTargetType;
import in.hocg.eagle.modules.crm.entity.CommentTarget;
import in.hocg.eagle.modules.crm.mapper.CommentTargetMapper;
import in.hocg.eagle.modules.crm.service.CommentTargetService;
import in.hocg.eagle.utils.ValidUtils;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long getOrCreateCommentTarget(CommentTargetType relType, Long relId) {
        final Optional<Long> idOpt = getCommentTarget(relType, relId);
        return idOpt.orElseGet(() -> {
            final CommentTarget entity = new CommentTarget()
                .setRelId(relId)
                .setRelType(relType.getCode());
            validInsert(entity);
            return entity.getId();
        });
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Long> getCommentTarget(CommentTargetType relType, Long relId) {
        ValidUtils.notNull(relType);
        ValidUtils.notNull(relId);
        final Integer relTypeCode = relType.getCode();
        return selectOneByRelTypeAndRelId(relTypeCode, relId);
    }

    private Optional<Long> selectOneByRelTypeAndRelId(Integer relType, Long relId) {
        return lambdaQuery().eq(CommentTarget::getRelType, relType)
            .eq(CommentTarget::getRelId, relId)
            .oneOpt()
            .map(CommentTarget::getId);
    }

}
