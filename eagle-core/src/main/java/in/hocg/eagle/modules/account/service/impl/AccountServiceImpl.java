package in.hocg.eagle.modules.account.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.Tree;
import in.hocg.eagle.basic.constant.GlobalConstant;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.datadict.Platform;
import in.hocg.eagle.mapstruct.AccountMapping;
import in.hocg.eagle.mapstruct.AuthorityMapping;
import in.hocg.eagle.mapstruct.RoleMapping;
import in.hocg.eagle.mapstruct.qo.account.AccountSearchQo;
import in.hocg.eagle.mapstruct.qo.account.AccountUpdateStatusPutQo;
import in.hocg.eagle.mapstruct.qo.account.GrantRoleQo;
import in.hocg.eagle.mapstruct.vo.account.AccountComplexVo;
import in.hocg.eagle.mapstruct.vo.account.AccountSearchVo;
import in.hocg.eagle.mapstruct.vo.account.IdAccountComplexVo;
import in.hocg.eagle.mapstruct.vo.authority.AuthorityTreeNodeVo;
import in.hocg.eagle.mapstruct.vo.role.RoleComplexVo;
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
import java.util.Objects;
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
    public IdAccountComplexVo selectOneComplexAndRole(Long id) {
        final Account account = baseMapper.selectById(id);
        VerifyUtils.notNull(account, "账号不存在");
        final List<Role> roles = roleAccountService.selectListRoleByAccountId(id, GlobalConstant.CURRENT_PLATFORM.getCode());
        List<Authority> authorities;
        List<RoleComplexVo> roleComplexes = Lists.newArrayList();
        for (Role role : roles) {
            authorities = roleAuthorityService.selectListAuthorityByRoleIdAndEnabled(role.getId(), Enabled.On.getCode());
            roleComplexes.add(roleMapping.asRoleComplexVo(role, authorities));
        }
        return mapping.asIdAccountComplexVo(account, roleComplexes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountComplexVo selectOneComplex(Long id) {
        final Account account = baseMapper.selectById(id);
        VerifyUtils.notNull(account, "账号不存在");
        return mapping.asAccountComplexVo(account);
    }

    @Override
    public Optional<Account> selectOneByUsername(String username) {
        return baseMapper.selectOneByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(GrantRoleQo qo) {
        final Long accountId = qo.getId();
        final Integer platform = Platform.Eagle.getCode();
        final List<Long> currentRoleIds = roleAccountService.selectListRoleByAccountId(accountId, platform)
            .stream().map(Role::getId).collect(Collectors.toList());

        final List<Long> roleIds = qo.getRoles();
        // 删除
        for (Long roleId : currentRoleIds) {
            if (!roleIds.contains(roleId)) {
                roleAccountService.deleteByAccountIdAndRoleId(accountId, roleId);
            }
        }

        // 新增
        for (Long roleId : roleIds) {
            if (!currentRoleIds.contains(roleId)) {
                roleAccountService.grantRole(accountId, roleId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Authority> selectListAuthorityById(Long accountId, Integer platform) {
        List<Authority> authorities = roleAccountService.selectListAuthorityByAccountId(accountId, platform);
        return authorities.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Role> selectListRoleById(Long accountId, Integer platform) {
        return roleAccountService.selectListRoleByAccountId(accountId, platform);
    }

    @Override
    public List<AuthorityTreeNodeVo> selectAuthorityTreeByCurrentAccount(Long accountId, Integer platform) {
        final List<Authority> authorities = selectListAuthorityById(accountId, platform);
        return Tree.getChild(null, authorities.stream()
            .map(authorityMapping::asAuthorityTreeNodeVo)
            .collect(Collectors.toList()));
    }

    @Override
    public IPage<AccountSearchVo> search(AccountSearchQo qo) {
        return baseMapper.search(qo, qo.page()).convert(mapping::asAccountSearchVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(AccountUpdateStatusPutQo qo) {
        Account entity = mapping.asAccount(qo);
        entity.setLastUpdatedAt(qo.getCreatedAt());
        entity.setLastUpdater(qo.getUserId());
        validUpdateById(entity);
    }

    @Override
    public void validEntity(Account entity) {
        final Long creatorId = entity.getCreator();
        final Long lastUpdaterId = entity.getLastUpdater();
        if (Objects.nonNull(creatorId)) {
            VerifyUtils.notNull(baseMapper.selectById(creatorId));
        }
        if (Objects.nonNull(lastUpdaterId)) {
            VerifyUtils.notNull(baseMapper.selectById(lastUpdaterId));
        }
    }
}
