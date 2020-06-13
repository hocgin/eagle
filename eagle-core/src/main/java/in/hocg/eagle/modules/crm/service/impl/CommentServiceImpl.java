package in.hocg.eagle.modules.crm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.MessageContext;
import in.hocg.eagle.basic.constant.CodeEnum;
import in.hocg.eagle.basic.constant.datadict.*;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.mybatis.tree.TreeServiceImpl;
import in.hocg.eagle.modules.crm.mapstruct.CommentMapping;
import in.hocg.eagle.modules.crm.entity.Comment;
import in.hocg.eagle.modules.crm.mapper.CommentMapper;
import in.hocg.eagle.modules.crm.pojo.qo.comment.ChildCommentPagingQo;
import in.hocg.eagle.modules.crm.pojo.qo.comment.CommentPostQo;
import in.hocg.eagle.modules.crm.pojo.qo.comment.CommentPutQo;
import in.hocg.eagle.modules.crm.pojo.qo.comment.RootCommentPagingQo;
import in.hocg.eagle.modules.crm.pojo.vo.comment.CommentComplexVo;
import in.hocg.eagle.modules.crm.pojo.vo.comment.RootCommentComplexVo;
import in.hocg.eagle.modules.crm.service.CommentService;
import in.hocg.eagle.modules.crm.service.CommentTargetService;
import in.hocg.eagle.modules.mms.message.event.SubscriptionEvent;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.web.ResultUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * <p>
 * [评论模块] 评论表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-08
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CommentServiceImpl extends TreeServiceImpl<CommentMapper, Comment> implements CommentService {

    private final CommentTargetService commentTargetService;
    private final AccountService accountService;
    private final CommentMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(CommentPutQo qo) {
        final Comment entity = mapping.asComment(qo);
        entity.setLastUpdatedAt(qo.getCreatedAt());
        entity.setLastUpdater(qo.getUserId());
        validUpdateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(CommentPostQo qo) throws Throwable {
        final Long creatorId = qo.getUserId();
        final Integer targetTypeCode = qo.getRefType();
        final Long refId = qo.getRefId();
        final CommentTargetType targetType = CodeEnum.of(targetTypeCode, CommentTargetType.class)
            .orElseThrow((Supplier<Throwable>) () -> ServiceException.wrap("参数错误"));
        final Long targetId = commentTargetService.getOrCreateCommentTarget(targetType, refId);

        final Comment entity = mapping.asComment(qo);
        entity.setEnabled(Enabled.On.getCode());
        entity.setTargetId(targetId);
        entity.setCreatedAt(qo.getCreatedAt());
        entity.setCreator(creatorId);
        validInsert(entity);

        // 触发消息
        MessageContext.publish(new SubscriptionEvent()
            .setActorId(creatorId)
            .setSubjectId(entity.getId())
            .setSubjectType(SubjectType.Comment)
            .setNotifyType(NotifyType.SubscriptionComment));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<RootCommentComplexVo> pagingRootComment(RootCommentPagingQo qo) throws Throwable {
        final Integer targetTypeCode = qo.getRefType();
        final Long refId = qo.getRefId();
        final Optional<CommentTargetType> targetTypeOpt = CodeEnum.of(targetTypeCode, CommentTargetType.class);
        if (!targetTypeOpt.isPresent()) {
            return ResultUtils.emptyPage(qo);
        }

        final CommentTargetType targetType = targetTypeOpt.get();
        final Optional<Long> targetIdOpt = commentTargetService.getCommentTarget(targetType, refId);
        if (!targetIdOpt.isPresent()) {
            return ResultUtils.emptyPage(qo);
        }

        final Long targetId = targetIdOpt.get();
        final IPage<Comment> result = baseMapper.pagingRootCommend(targetId, Enabled.On.getCode(), qo.page());
        return result.convert(entity -> {
            final RootCommentComplexVo item = mapping.asRootCommentComplexVo(this.convertComplex(entity));
            final String treePath = entity.getTreePath() + "/";
            item.setChildrenTotal(countRightLikeTreePath(treePath));
            return item;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<CommentComplexVo> pagingChildComment(ChildCommentPagingQo qo) {
        final Comment pComment = baseMapper.selectById(qo.getParentId());
        if (Objects.isNull(pComment)
            || Objects.isNull(pComment.getParentId())
            || LangUtils.equals(pComment.getEnabled(), Enabled.Off.getCode())) {
            return ResultUtils.emptyPage(qo);
        }

        final String treePath = pComment.getTreePath();
        final String regexTreePath = String.format("%s/.*", treePath);
        final IPage<Comment> result = baseMapper.pagingByRegexTreePath(regexTreePath, qo.page());
        return result.convert(this::convertComplex);
    }

    private CommentComplexVo convertComplex(Comment entity) {
        final CommentComplexVo result = mapping.asCommentComplexVo(entity);
        final String content = LangUtils.equals(entity.getEnabled(), Enabled.On.getCode())
            ? result.getContent()
            : "已删除";
        result.setContent(content);
        final Long parentId = entity.getParentId();
        result.setCommenter(accountService.selectOneComplex(result.getCommenterId()));
        if (Objects.nonNull(parentId)) {
            final Comment pComment = baseMapper.selectById(parentId);
            final Long pCommentCreatorId = pComment.getCreator();
            result.setPCommenterId(pCommentCreatorId);
            result.setPCommenter(accountService.selectOneComplex(pCommentCreatorId));
        }
        return result;
    }

    public Integer countRightLikeTreePath(String treePath) {
        return lambdaQuery().likeRight(Comment::getTreePath, treePath).count();
    }

    @Override
    public void validEntity(Comment entity) {
        super.validEntity(entity);
        final Long targetId = entity.getTargetId();
        if (Objects.nonNull(targetId)) {
            ValidUtils.notNull(commentTargetService.getById(targetId));
        }
    }
}
