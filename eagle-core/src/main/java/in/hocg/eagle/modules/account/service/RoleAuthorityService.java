package in.hocg.eagle.modules.account.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.Role;
import in.hocg.eagle.modules.account.entity.RoleAuthority;

import java.util.List;

/**
 * <p>
 * [用户模块] 角色-权限表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
public interface RoleAuthorityService extends AbstractService<RoleAuthority> {

    /**
     * 检查 authority 是否正在被角色使用
     *
     * @param regexTreePath
     * @return
     */
    boolean isUsedAuthority(String regexTreePath);

    /**
     * 给角色授权
     *
     * @param roleId
     * @param authorityId
     */
    void grantAuthority(Long roleId, Long authorityId);

    /**
     * 删除角色相关的权限绑定
     *
     * @param roleId
     */
    void deleteByRoleId(Long roleId);

    /**
     * 查询角色关联的权限
     *
     * @param roleId
     * @param enabled
     * @return
     */
    List<Authority> selectListAuthorityByRoleIdAndEnabled(Long roleId, Integer enabled);

    /**
     * 查找角色所具备的权限列表
     *
     * @param roleIds
     * @return
     */
    List<Authority> selectListAuthorityByRoleIds(List<Long> roleIds);

    /**
     * 查询使用该权限的角色
     *
     * @param id
     * @return
     */
    List<Role> selectListRoleByAuthorityId(Long id);

    void deleteByRoleIdAndAuthorityId(Long roleId, Long authorityId);
}
