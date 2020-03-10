package in.hocg.eagle.modules.comment.mapper;

import in.hocg.eagle.modules.comment.entity.CommentTarget;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [评论模块] 评论对象表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-08
 */
@Mapper
public interface CommentTargetMapper extends BaseMapper<CommentTarget> {

}
