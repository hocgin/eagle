package in.hocg.eagle.modules.account.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.RoleAccount;

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
    boolean isUsedRole(Integer roleId);
    
    /**
     * 授权角色给账号
     *
     * @param accountId
     * @param roleId
     */
    void grantRole(Integer accountId, Integer roleId);
    
    /**
     * 查找账号所具备的权限
     *
     * @param accountId
     * @return
     */
    List<Authority> selectListAuthorityByAccountId(Integer accountId);
}
