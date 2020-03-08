package in.hocg.eagle.modules.comment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.mapstruct.qo.comment.CommentPostQo;
import in.hocg.eagle.mapstruct.qo.comment.G2ndAfterCommentPagingQo;
import in.hocg.eagle.mapstruct.qo.comment.RootCommentPagingQo;
import in.hocg.eagle.mapstruct.vo.comment.CommentComplexVo;
import in.hocg.eagle.mapstruct.vo.comment.RootCommentComplexVo;
import in.hocg.eagle.modules.comment.entity.Comment;

/**
 * <p>
 * [评论模块] 评论表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-08
 */
public interface CommentService extends AbstractService<Comment> {

    /**
     * 发表评论
     *
     * @param qo qo
     * @throws Throwable
     */
    void comment(CommentPostQo qo) throws Throwable;

    /**
     * 查询根级评论
     *
     * @param qo qo
     * @return
     * @throws Throwable
     */
    IPage<RootCommentComplexVo> pagingRootComment(RootCommentPagingQo qo) throws Throwable;

    /**
     * 查询根评论的子评论
     *
     * @param qo 父级评论ID
     * @return
     */
    IPage<CommentComplexVo> paging2ndAfterComment(G2ndAfterCommentPagingQo qo);
}
