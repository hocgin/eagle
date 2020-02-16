package in.hocg.eagle.modules.account.service.impl;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.qo.account.GrantAuthorityQo;
import in.hocg.eagle.mapstruct.qo.account.GrantRoleQo;
import in.hocg.eagle.mapstruct.vo.IdAccountComplexVo;
import in.hocg.eagle.modules.account.entity.Account;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.Role;
import in.hocg.eagle.modules.account.mapper.AccountMapper;
import in.hocg.eagle.modules.account.service.AccountService;
import in.hocg.eagle.modules.account.service.AuthorityAccountService;
import in.hocg.eagle.modules.account.service.RoleAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * [用户模块] 账号表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountServiceImpl extends AbstractServiceImpl<AccountMapper, Account> implements AccountService {
    
    private final RoleAccountService roleAccountService;
    private final AuthorityAccountService authorityAccountService;
    
    @Override
    public IdAccountComplexVo selectOneComplex(Serializable id) {
        return null;
    }
    
    @Override
    public Optional<Account> selectOneByUsername(String username) {
        return baseMapper.selectOneByUsername(username);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(GrantRoleQo qo) {
        final Integer accountId = qo.getId();
        qo.getRoles().forEach(roleId -> roleAccountService.grantRole(accountId, roleId));
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantAuthority(GrantAuthorityQo qo) {
        final Integer accountId = qo.getId();
        qo.getAuthorities().forEach(authorityId -> authorityAccountService.grantAuthority(accountId, authorityId));
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Authority> selectListAuthorityById(Integer accountId) {
        List<Authority> authorities = Lists.newArrayList();
        authorities.addAll(roleAccountService.selectListAuthorityByAccountId(accountId));
        authorities.addAll(authorityAccountService.selectListAuthorityByAccountId(accountId));
        return authorities.stream().distinct().collect(Collectors.toList());
    }
    
    @Override
    public List<Role> selectListRoleById(Integer accountId) {
        return roleAccountService.selectListRoleByAccountId(accountId);
    }
}
