package in.hocg.eagle.modules.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.comment.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [评论模块] 评论表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-08
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<Comment> pagingRootCommend(@Param("targetId") Long targetId,
                                     @Param("enabled") Integer enabled,
                                     Page page);

    IPage<Comment> pagingByRegexTreePath(@Param("regexTreePath") String regexTreePath);
}
