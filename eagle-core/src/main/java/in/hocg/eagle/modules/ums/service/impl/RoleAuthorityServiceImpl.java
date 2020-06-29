package in.hocg.eagle.modules.ums.service.impl;

import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.modules.ums.entity.Authority;
import in.hocg.eagle.modules.ums.entity.Role;
import in.hocg.eagle.modules.ums.entity.RoleAuthority;
import in.hocg.eagle.modules.ums.mapper.RoleAuthorityMapper;
import in.hocg.eagle.modules.ums.service.AuthorityService;
import in.hocg.eagle.modules.ums.service.RoleAuthorityService;
import in.hocg.eagle.modules.ums.service.RoleService;
import in.hocg.eagle.utils.ValidUtils;
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
    public void grantAuthority(Long roleId, Long authorityId) {
        final Role role = roleService.getById(roleId);
        ValidUtils.notNull(role, "授权失败");
        Authority authority = authorityService.getById(authorityId);
        ValidUtils.notNull(authority, "授权失败");

        // 已经具备权限
        if (isHasAuthority(roleId, authorityId)) {
            return;
        }
        RoleAuthority entity = new RoleAuthority()
                .setAuthorityId(authorityId)
                .setRoleId(roleId);
        validInsert(entity);
    }

    private boolean isHasAuthority(Long roleId, Long authorityId) {
        return baseMapper.countByRoleIdAndAuthorityId(roleId, authorityId) > 0;
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        baseMapper.deleteByRoleId(roleId);
    }

    @Override
    public List<Authority> selectListAuthorityByRoleIdAndEnabled(Long roleId, Integer enabled) {
        return baseMapper.selectListAuthorityByRoleIdAndEnabled(roleId, enabled);
    }

    @Override
    public List<Authority> selectListAuthorityByRoleIds(List<Long> roleIds) {
        return baseMapper.selectListAuthorityByRoleIdsAndEnabled(roleIds, Enabled.On.getCode());
    }

    @Override
    public List<Role> selectListRoleByAuthorityId(Long id) {
        return baseMapper.selectListRoleByAuthorityId(id);
    }

    @Override
    public void deleteByRoleIdAndAuthorityId(Long roleId, Long authorityId) {
        baseMapper.deleteByRoleIdAndAuthorityId(roleId, authorityId);
    }


}
