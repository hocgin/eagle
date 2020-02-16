package in.hocg.eagle.modules.account.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.AuthorityAccount;

import java.util.Collection;

/**
 * <p>
 * [用户模块] 权限-账号表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
public interface AuthorityAccountService extends AbstractService<AuthorityAccount> {
    
    /**
     * 查询权限是否正在使用
     *
     * @param regexTreePath
     * @return
     */
    boolean isUsedAuthority(String regexTreePath);
    
    /**
     * 授权权限给账号
     *
     * @param accountId   账号ID
     * @param authorityId 权限ID
     */
    void grantAuthority(Integer accountId, Integer authorityId);
    
    /**
     * 查找账号所具备的权限
     *
     * @param accountId
     * @return
     */
    Collection<? extends Authority> selectListAuthorityByAccountId(Integer accountId);
}
