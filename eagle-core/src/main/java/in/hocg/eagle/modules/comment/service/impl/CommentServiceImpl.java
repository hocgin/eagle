package in.hocg.eagle.modules.comment.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.CommentTargetType;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.mapstruct.CommentMapping;
import in.hocg.eagle.mapstruct.qo.comment.CommentPostQo;
import in.hocg.eagle.mapstruct.qo.comment.CommentPutQo;
import in.hocg.eagle.mapstruct.qo.comment.G2ndAfterCommentPagingQo;
import in.hocg.eagle.mapstruct.qo.comment.RootCommentPagingQo;
import in.hocg.eagle.mapstruct.vo.comment.CommentComplexVo;
import in.hocg.eagle.mapstruct.vo.comment.RootCommentComplexVo;
import in.hocg.eagle.modules.account.service.AccountService;
import in.hocg.eagle.modules.comment.entity.Comment;
import in.hocg.eagle.modules.comment.mapper.CommentMapper;
import in.hocg.eagle.modules.comment.service.CommentService;
import in.hocg.eagle.modules.comment.service.CommentTargetService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ResultUtils;
import in.hocg.eagle.utils.VerifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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
public class CommentServiceImpl extends AbstractServiceImpl<CommentMapper, Comment> implements CommentService {

    private final CommentTargetService commentTargetService;
    private final AccountService accountService;
    private final CommentMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateComment(CommentPutQo qo) {
        final Comment entity = mapping.asComment(qo);
        entity.setLastUpdatedAt(qo.getCreatedAt());
        entity.setLastUpdater(qo.getUserId());
        updateOne(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void comment(CommentPostQo qo) throws Throwable {
        final String targetTypeCode = qo.getTargetTypeCode();
        final Long id = qo.getId();
        final CommentTargetType targetType = IntEnum.of(targetTypeCode, CommentTargetType.class)
            .orElseThrow((Supplier<Throwable>) () -> ServiceException.wrap("参数错误"));
        final Long targetId = commentTargetService.getOrCreateCommentTarget(targetType, id);

        final Long parentId = qo.getParentId();
        StringBuilder path = new StringBuilder();

        if (Objects.nonNull(parentId)) {
            Comment parent = baseMapper.selectById(parentId);
            VerifyUtils.notNull(parent, "父级不存在");
            path.append(parent.getTreePath());
        }

        final Comment entity = mapping.asComment(qo);
        entity.setEnabled(Enabled.On.getCode());
        entity.setTargetId(targetId);
        entity.setTreePath("/tmp");
        entity.setCreatedAt(qo.getCreatedAt());
        entity.setCreator(qo.getUserId());
        insertOne(entity);
        path.append(String.format("/%d", entity.getId()));
        entity.setTreePath(path.toString());
        updateOne(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<RootCommentComplexVo> pagingRootComment(RootCommentPagingQo qo) throws Throwable {
        final String targetTypeCode = qo.getTargetTypeCode();
        final Long id = qo.getId();
        final CommentTargetType targetType = IntEnum.of(targetTypeCode, CommentTargetType.class)
            .orElseThrow((Supplier<Throwable>) () -> ServiceException.wrap("参数错误"));
        final Long targetId = commentTargetService.getCommentTarget(targetType, id)
            .orElseThrow((Supplier<Throwable>) () -> ServiceException.wrap("未找到评论"));
        final IPage<Comment> result = baseMapper.pagingRootCommend(targetId, Enabled.On.getCode(), qo.page());
        return result.convert(entity -> {
            final RootCommentComplexVo item = mapping.asRootCommentComplexVo(this.convert(entity));
            final String treePath = entity.getTreePath();
            item.setChildrenTotal(countRightLikeTreePath(treePath));
            return item;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<CommentComplexVo> paging2ndAfterComment(G2ndAfterCommentPagingQo qo) {
        final Comment pComment = baseMapper.selectById(qo.getParentId());
        if (LangUtils.equals(pComment.getEnabled(), Enabled.Off.getCode())) {
            return ResultUtils.emptyPage(qo);
        }

        VerifyUtils.notNull(pComment);
        VerifyUtils.isNull(pComment.getParentId(), "非根评论");
        final String treePath = pComment.getTreePath();
        final String regexTreePath = String.format("%s/.*", treePath);
        final IPage<Comment> result = baseMapper.pagingByRegexTreePath(regexTreePath, qo.page());
        return result.convert(this::convert);
    }

    private CommentComplexVo convert(Comment entity) {
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
    public void verifyEntity(Comment entity) {
        final Long parentId = entity.getParentId();
        final Long targetId = entity.getTargetId();

        if (Objects.nonNull(parentId)) {
            VerifyUtils.notNull(baseMapper.selectById(parentId));
        }
        if (Objects.nonNull(targetId)) {
            VerifyUtils.notNull(commentTargetService.getById(targetId));
        }
    }
}
