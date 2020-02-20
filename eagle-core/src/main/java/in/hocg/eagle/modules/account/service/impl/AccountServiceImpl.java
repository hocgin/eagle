package in.hocg.eagle.modules.account.service.impl;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.Tree;
import in.hocg.eagle.basic.constant.Enabled;
import in.hocg.eagle.mapstruct.AccountMapping;
import in.hocg.eagle.mapstruct.AuthorityMapping;
import in.hocg.eagle.mapstruct.RoleMapping;
import in.hocg.eagle.mapstruct.qo.account.GrantRoleQo;
import in.hocg.eagle.mapstruct.vo.AuthorityTreeNodeVo;
import in.hocg.eagle.mapstruct.vo.IdAccountComplexVo;
import in.hocg.eagle.mapstruct.vo.RoleComplexVo;
import in.hocg.eagle.modules.account.entity.Account;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.Role;
import in.hocg.eagle.modules.account.mapper.AccountMapper;
import in.hocg.eagle.modules.account.service.AccountService;
import in.hocg.eagle.modules.account.service.RoleAccountService;
import in.hocg.eagle.modules.account.service.RoleAuthorityService;
import in.hocg.eagle.utils.VerifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final RoleAuthorityService roleAuthorityService;
    private final AccountMapping mapping;
    private final RoleMapping roleMapping;
    private final AuthorityMapping authorityMapping;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IdAccountComplexVo selectOneComplex(Long id) {
        final Account account = baseMapper.selectById(id);
        VerifyUtils.notNull(account, "账号不存在");
        final List<Role> roles = roleAccountService.selectListRoleByAccountId(id);
        List<Authority> authorities;
        List<RoleComplexVo> roleComplexes = Lists.newArrayList();
        for (Role role : roles) {
            authorities = roleAuthorityService.selectListAuthorityByRoleIdAndEnabled(role.getId(), Enabled.On.getCode());
            roleComplexes.add(roleMapping.asRoleComplexVo(role, authorities));
        }
        return mapping.asIdAccountComplexVo(account, roleComplexes);
    }
    
    @Override
    public Optional<Account> selectOneByUsername(String username) {
        return baseMapper.selectOneByUsername(username);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(GrantRoleQo qo) {
        final Long accountId = qo.getId();
        qo.getRoles().forEach(roleId -> roleAccountService.grantRole(accountId, roleId));
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Authority> selectListAuthorityById(Long accountId) {
        List<Authority> authorities = roleAccountService.selectListAuthorityByAccountId(accountId);
        return authorities.stream().distinct().collect(Collectors.toList());
    }
    
    @Override
    public List<Role> selectListRoleById(Long accountId) {
        return roleAccountService.selectListRoleByAccountId(accountId);
    }
    
    @Override
    public List<AuthorityTreeNodeVo> selectAuthorityTreeByCurrentAccount(Long accountId) {
        final List<Authority> authorities = selectListAuthorityById(accountId);
        return Tree.getChild(null, authorities.stream()
                .map(authorityMapping::asAuthorityTreeNodeVo)
                .collect(Collectors.toList()));
    }
}
