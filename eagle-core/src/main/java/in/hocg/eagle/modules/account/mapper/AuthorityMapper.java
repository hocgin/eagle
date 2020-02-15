package in.hocg.eagle.modules.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.account.entity.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [用户模块] 权限表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Mapper
public interface AuthorityMapper extends BaseMapper<Authority> {
    
    /**
     * 根据 regex 匹配 tree_path 来更新节点的状态
     *
     * @param regexTreePath
     */
    int updateOffStatusByRegexTreePath(@Param("regexTreePath") String regexTreePath);
    
    /**
     * 指定新的 tree_path 并根据 regex 替换旧的 tree_path
     *
     * @param regexTreePath
     * @param oldTreePath
     * @param newTreePath
     */
    int updateTreePathByRegexTreePath(@Param("regexTreePath") String regexTreePath,
                                       @Param("oldTreePath") String oldTreePath,
                                       @Param("newTreePath") String newTreePath);
    
    /**
     * 查找 parent_id 符合的数据
     *
     * @param parentId
     * @return
     */
    List<Authority> selectListByParentId(@Param("parentId") Integer parentId);
    
    /**
     * 获取 tree_path 匹配的数据
     *
     * @param regexTreePath
     * @return
     */
    List<Authority> selectListChildrenByRegexTreePath(@Param("regexTreePath") String regexTreePath);
    
    /**
     * 删除 tree_path 匹配的数据
     *
     * @param regexTreePath
     * @return
     */
    int deleteListByRegexTreePath(String regexTreePath);
}
