package in.hocg.eagle.modules.ums.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.ums.pojo.vo.account.AccountComplexVo;
import in.hocg.eagle.modules.ums.pojo.vo.account.IdAccountComplexVo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.ums.entity.Account;
import in.hocg.eagle.modules.ums.entity.Authority;
import in.hocg.eagle.modules.ums.entity.Role;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountSearchQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountUpdateStatusQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.GrantRoleQo;

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
     * 获取账号详情和角色列表
     *
     * @param id
     * @return
     */
    IdAccountComplexVo selectOneComplexAndRole(Long id);

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    AccountComplexVo selectOneComplex(Long id);

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

    /**
     * 搜索账号列表
     *
     * @param qo
     * @return
     */
    IPage<AccountComplexVo> search(AccountSearchQo qo);

    void updateStatus(AccountUpdateStatusQo qo);
}
