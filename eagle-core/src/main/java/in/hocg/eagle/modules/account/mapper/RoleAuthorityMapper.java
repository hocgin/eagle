package in.hocg.eagle.modules.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.RoleAuthority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    
    /**
     * 查询角色关联的权限列表
     *
     * @param roleId
     * @param enabled
     * @return
     */
    List<Authority> selectListAuthorityByRoleIdAndEnabled(@Param("roleId") Integer roleId, @Param("enabled") Integer enabled);
    
    /**
     * 查询 role_id 与 authority_id 符合条件的数量
     * @param roleId
     * @param authorityId
     * @return
     */
    Integer countByRoleIdAndAuthorityId(@Param("roleId") Integer roleId, @Param("authorityId") Integer authorityId);
}
