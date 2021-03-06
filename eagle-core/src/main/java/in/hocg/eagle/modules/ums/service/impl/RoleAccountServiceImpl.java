package in.hocg.eagle.modules.ums.service.impl;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.modules.ums.entity.Account;
import in.hocg.eagle.modules.ums.entity.Authority;
import in.hocg.eagle.modules.ums.entity.Role;
import in.hocg.eagle.modules.ums.entity.RoleAccount;
import in.hocg.eagle.modules.ums.mapper.RoleAccountMapper;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.eagle.modules.ums.service.RoleAccountService;
import in.hocg.eagle.modules.ums.service.RoleService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * [用户模块] 角色-账号表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RoleAccountServiceImpl extends AbstractServiceImpl<RoleAccountMapper, RoleAccount> implements RoleAccountService {

    private final AccountService accountService;
    private final RoleService roleService;

    @Override
    public boolean isUsedRole(Long roleId) {
        return baseMapper.countByRoleId(roleId) > 0;
    }

    @Override
    public void grantRole(Long accountId, Long roleId) {
        final Account account = accountService.getById(accountId);
        ValidUtils.notNull(account, "授权失败");
        final Role role = roleService.getById(roleId);
        ValidUtils.notNull(role, "授权失败");
        if (isHasRole(accountId, roleId)) {
            return;
        }
        final RoleAccount insert = new RoleAccount()
                .setAccountId(accountId)
                .setRoleId(roleId);
        baseMapper.insert(insert);
    }

    @Override
    public List<Authority> selectListAuthorityByAccountId(Long accountId, Integer platform) {
        final List<Role> roles = baseMapper.selectListRoleByAccountIdAndEnabled(accountId, platform, Enabled.On.getCode());
        final List<Long> roleIds = LangUtils.toList(roles, Role::getId);
        if (roleIds.isEmpty()) {
            return Lists.newArrayList();
        }
        return roleService.selectListAuthorityByIds(roleIds);
    }

    @Override
    public List<Role> selectListRoleByAccountId(Long accountId, Integer platform) {
        return baseMapper.selectListRoleByAccountIdAndEnabled(accountId, platform, Enabled.On.getCode());
    }

    @Override
    public void deleteByAccountIdAndRoleId(Long accountId, Long roleId) {
        baseMapper.deleteByAccountIdAndRoleId(accountId, roleId);
    }

    private boolean isHasRole(Long accountId, Long roleId) {
        return baseMapper.countByAccountIdAndRoleId(accountId, roleId) > 0;
    }

}
