package in.hocg.eagle.modules.account.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.Role;
import in.hocg.eagle.modules.account.entity.RoleAuthority;
import in.hocg.eagle.modules.account.mapper.RoleAuthorityMapper;
import in.hocg.eagle.modules.account.service.AuthorityService;
import in.hocg.eagle.modules.account.service.RoleAuthorityService;
import in.hocg.eagle.modules.account.service.RoleService;
import in.hocg.eagle.utils.VerifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * [用户模块] 角色-权限表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RoleAuthorityServiceImpl extends AbstractServiceImpl<RoleAuthorityMapper, RoleAuthority>
        implements RoleAuthorityService {
    
    private final RoleService roleService;
    private final AuthorityService authorityService;
    
    @Override
    public boolean isUsedAuthority(String regexTreePath) {
        return baseMapper.selectListByAuthorityRegexTreePath(regexTreePath) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantAuthority(Integer roleId, List<Integer> authorities) {
        final Role role = roleService.getById(roleId);
        VerifyUtils.notNull(role, "角色不存在");
        RoleAuthority roleAuthority;
        for (Integer authorityId : authorities) {
            // 已经具备权限
            if (isHasAuthority(roleId, authorityId)) {
                continue;
            }
            Authority authority = authorityService.getById(authorityId);
            VerifyUtils.notNull(authority, "授权失败");
            roleAuthority = new RoleAuthority()
                    .setAuthorityId(authorityId)
                    .setRoleId(roleId);
            baseMapper.insert(roleAuthority);
        }
    }
    
    private boolean isHasAuthority(Integer roleId, Integer authorityId) {
        return baseMapper.countByRoleIdAndAuthorityId(roleId, authorityId) > 0;
    }
    
    @Override
    public void deleteByRoleId(Integer roleId) {
        baseMapper.deleteByRoleId(roleId);
    }
    
    @Override
    public List<Authority> selectListAuthorityByRoleIdAndEnabled(Integer roleId, Integer enabled) {
        return baseMapper.selectListAuthorityByRoleIdAndEnabled(roleId, enabled);
    }
    
    
}
