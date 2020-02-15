package in.hocg.eagle.modules.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.account.entity.RoleAuthority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [用户模块] 角色-权限表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Mapper
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthority> {
    
    /**
     * 查询有符合 tree_path 的关联关系
     *
     * @param regexTreePath
     * @return
     */
    Integer selectListByAuthorityRegexTreePath(@Param("regexTreePath") String regexTreePath);
    
    /**
     * 删除角色相关的权限绑定
     *
     * @param roleId
     * @return
     */
    Integer deleteByRoleId(@Param("roleId") Integer roleId);
}
