package in.hocg.eagle.modules.account.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.account.entity.RoleAccount;

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
}
