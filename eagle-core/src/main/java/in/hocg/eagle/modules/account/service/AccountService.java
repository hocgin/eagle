package in.hocg.eagle.modules.account.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.mapstruct.qo.account.GrantRoleQo;
import in.hocg.eagle.mapstruct.vo.AuthorityTreeNodeVo;
import in.hocg.eagle.mapstruct.vo.IdAccountComplexVo;
import in.hocg.eagle.modules.account.entity.Account;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.Role;

import java.util.List;
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
    IdAccountComplexVo selectOneComplex(Long id);
    
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
     * 查找账号具备的所有权限
     *
     * @param accountId
     * @return
     */
    List<Authority> selectListAuthorityById(Long accountId, Integer platform);
    
    /**
     * 查找账号具备的所有角色
     *
     * @param accountId
     * @return
     */
    List<Role> selectListRoleById(Long accountId, Integer platform);
    
    /**
     * 获取权限树(当前用户)
     *
     * @param accountId
     */
    List<AuthorityTreeNodeVo> selectAuthorityTreeByCurrentAccount(Long accountId, Integer platform);
}
