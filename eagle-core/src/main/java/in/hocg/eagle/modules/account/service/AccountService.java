package in.hocg.eagle.modules.account.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.mapstruct.qo.account.GrantAuthorityQo;
import in.hocg.eagle.mapstruct.qo.account.GrantRoleQo;
import in.hocg.eagle.mapstruct.vo.IdAccountComplexVo;
import in.hocg.eagle.modules.account.entity.Account;

import java.io.Serializable;
import java.util.Optional;

/**
 * <p>
 * [用户模块] 账号表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
public interface AccountService extends AbstractService<Account> {
    
    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    IdAccountComplexVo selectOneComplex(Serializable id);
    
    /**
     * 根据 username 查找账号
     *
     * @param username
     * @return
     */
    Optional<Account> selectOneByUsername(String username);
    
    /**
     * 授权角色
     *
     * @param qo
     */
    void grantRole(GrantRoleQo qo);
    
    /**
     * 授权权限
     *
     * @param qo
     */
    void grantAuthority(GrantAuthorityQo qo);
}
