package in.hocg.eagle.modules.account.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.mapstruct.RoleMapping;
import in.hocg.eagle.mapstruct.qo.authority.GrantAuthorityQo;
import in.hocg.eagle.mapstruct.qo.role.RolePostQo;
import in.hocg.eagle.mapstruct.qo.role.RolePutQo;
import in.hocg.eagle.mapstruct.qo.role.RoleSearchQo;
import in.hocg.eagle.mapstruct.vo.role.RoleComplexVo;
import in.hocg.eagle.mapstruct.vo.role.RoleSearchVo;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.Role;
import in.hocg.eagle.modules.account.mapper.RoleMapper;
import in.hocg.eagle.modules.account.service.RoleAccountService;
import in.hocg.eagle.modules.account.service.RoleAuthorityService;
import in.hocg.eagle.modules.account.service.RoleService;
import in.hocg.eagle.utils.VerifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * [用户模块] 角色表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RoleServiceImpl extends AbstractServiceImpl<RoleMapper, Role> implements RoleService {
    private final RoleMapping mapping;
    private final RoleAuthorityService roleAuthorityService;
    private final RoleAccountService roleAccountService;

    private int insert(Role role) {
        final String roleCode = role.getRoleCode();
        if (hasRoleCode(roleCode)) {
            throw ServiceException.wrap("新增失败,角色码已经存在");
        }
        return baseMapper.insert(role);
    }

    public boolean hasRoleCode(String roleCode) {
        return hasRoleCodeIgnoreId(roleCode, null);
    }

    public boolean hasRoleCodeIgnoreId(String roleCode, Long id) {
        return lambdaQuery().eq(Role::getRoleCode, roleCode)
            .ne(Objects.nonNull(id), Role::getId, id)
            .count() > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(RolePostQo qo) {
        final Role role = mapping.asRole(qo);
        role.setCreatedAt(qo.getCreatedAt());
        role.setCreator(qo.getUserId());
        insert(role);
        final List<Long> authorities = qo.getAuthorities();
        if (authorities.isEmpty()) {
            return;
        }
        for (Long authorityId : authorities) {
            roleAuthorityService.grantAuthority(role.getId(), authorityId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(RolePutQo qo) {
        final Role role = mapping.asRole(qo);
        role.setLastUpdatedAt(qo.getCreatedAt());
        role.setLastUpdater(qo.getUserId());
        update(role);
    }

    private void update(Role entity) {
        if (hasRoleCodeIgnoreId(entity.getRoleCode(), entity.getId())) {
            throw ServiceException.wrap("更新失败,角色码已经存在");
        }
        baseMapper.updateById(entity);
    }

    @Override
    public IPage<RoleSearchVo> search(RoleSearchQo qo) {
        return baseMapper.search(qo, qo.page());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantAuthority(GrantAuthorityQo qo) {
        final Long roleId = qo.getId();
        final List<Long> currentAuthorityIds = roleAuthorityService.selectListAuthorityByRoleIds(Lists.newArrayList(roleId))
            .stream()
            .map(Authority::getId)
            .collect(Collectors.toList());
        final List<Long> authorityIds = qo.getAuthorities();

        // 删除
        for (Long authorityId : currentAuthorityIds) {
            if (!authorityIds.contains(authorityId)) {
                roleAuthorityService.deleteByRoleIdAndAuthorityId(roleId, authorityId);
            }
        }

        // 新增
        for (Long authorityId : authorityIds) {
            if (!currentAuthorityIds.contains(authorityId)) {
                roleAuthorityService.grantAuthority(roleId, authorityId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(Long id) {
        if (roleAccountService.isUsedRole(id)) {
            throw ServiceException.wrap("角色正在被账号使用");
        }
        baseMapper.deleteById(id);
        roleAuthorityService.deleteByRoleId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleComplexVo selectOne(Long id) {
        final Role role = baseMapper.selectById(id);
        VerifyUtils.notNull(role, "角色不存在");
        List<Authority> authorities = roleAuthorityService.selectListAuthorityByRoleIdAndEnabled(id, Enabled.On.getCode());
        return mapping.asRoleComplexVo(role, authorities);
    }

    @Override
    public List<Authority> selectListAuthorityByIds(List<Long> roleIds) {
        return roleAuthorityService.selectListAuthorityByRoleIds(roleIds);
    }
}
