package in.hocg.eagle.modules.account.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.Enabled;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.mapstruct.RoleMapping;
import in.hocg.eagle.mapstruct.qo.GrantAuthorityQo;
import in.hocg.eagle.mapstruct.qo.RolePostQo;
import in.hocg.eagle.mapstruct.qo.RolePutQo;
import in.hocg.eagle.mapstruct.qo.RoleSearchQo;
import in.hocg.eagle.mapstruct.vo.RoleComplexVo;
import in.hocg.eagle.mapstruct.vo.RoleSearchVo;
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
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(RolePostQo qo) {
        final Role role = mapping.asRole(qo);
        role.setCreatedAt(qo.getCreatedAt());
        role.setCreator(qo.getUserId());
        baseMapper.insert(role);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(RolePutQo qo) {
        final Role role = mapping.asRole(qo);
        role.setLastUpdatedAt(qo.getCreatedAt());
        role.setLastUpdater(qo.getUserId());
        baseMapper.updateById(role);
    }
    
    @Override
    public IPage<RoleSearchVo> search(RoleSearchQo qo) {
        return baseMapper.search(qo, qo.page());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantAuthority(GrantAuthorityQo qo) {
        final Long id = qo.getId();
        qo.getAuthorities().forEach(authorityId -> roleAuthorityService.grantAuthority(id, authorityId));
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
