package in.hocg.eagle.modules.crm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.web.AbstractService;
import in.hocg.eagle.modules.crm.entity.Comment;
import in.hocg.eagle.modules.crm.pojo.qo.comment.ChildCommentPagingQo;
import in.hocg.eagle.modules.crm.pojo.qo.comment.CommentPostQo;
import in.hocg.eagle.modules.crm.pojo.qo.comment.CommentPutQo;
import in.hocg.eagle.modules.crm.pojo.qo.comment.RootCommentPagingQo;
import in.hocg.basic.api.vo.CommentComplexVo;
import in.hocg.eagle.modules.crm.pojo.vo.comment.RootCommentComplexVo;

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
     * 更新评论(状态或内容)
     *
     * @param qo
     */
    void updateOne(CommentPutQo qo);

    /**
     * 发表评论
     *
     * @param qo qo
     * @throws Throwable
     */
    void insertOne(CommentPostQo qo) throws Throwable;

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
    IPage<CommentComplexVo> pagingChildComment(ChildCommentPagingQo qo);

    CommentComplexVo selectOne(Long id);
}
