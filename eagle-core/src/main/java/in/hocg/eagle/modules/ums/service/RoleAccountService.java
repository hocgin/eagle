package in.hocg.eagle.modules.ums.service;

import in.hocg.eagle.basic.ext.mybatis.basic.AbstractService;
import in.hocg.eagle.modules.ums.entity.Authority;
import in.hocg.eagle.modules.ums.entity.Role;
import in.hocg.eagle.modules.ums.entity.RoleAccount;

import java.util.List;

/**
 * <p>
 * [用户模块] 角色-账号表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
public interface RoleAccountService extends AbstractService<RoleAccount> {

    /**
     * 角色是否正在被使用
     *
     * @param roleId
     * @return
     */
    boolean isUsedRole(Long roleId);

    /**
     * 授权角色给账号
     *
     * @param accountId
     * @param roleId
     */
    void grantRole(Long accountId, Long roleId);

    /**
     * 查找账号所具备的权限
     *
     * @param accountId
     * @param platform
     * @return
     */
    List<Authority> selectListAuthorityByAccountId(Long accountId, Integer platform);

    /**
     * 查找账号所具备的角色
     *
     * @param accountId
     * @param platform
     * @return
     */
    List<Role> selectListRoleByAccountId(Long accountId, Integer platform);

    /**
     * 移除账户的角色
     *
     * @param accountId
     * @param roleId
     */
    void deleteByAccountIdAndRoleId(Long accountId, Long roleId);
}
