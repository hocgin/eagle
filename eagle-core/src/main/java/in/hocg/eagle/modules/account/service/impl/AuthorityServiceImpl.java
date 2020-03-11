package in.hocg.eagle.modules.account.service.impl;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.Tree;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.security.SecurityContext;
import in.hocg.eagle.mapstruct.AuthorityMapping;
import in.hocg.eagle.modules.account.pojo.qo.authority.AuthorityInsertQo;
import in.hocg.eagle.modules.account.pojo.qo.authority.AuthoritySearchQo;
import in.hocg.eagle.modules.account.pojo.qo.authority.AuthorityUpdateQo;
import in.hocg.eagle.modules.account.pojo.qo.role.GrantRoleQo;
import in.hocg.eagle.modules.account.pojo.vo.authority.AuthorityComplexAndRoleVo;
import in.hocg.eagle.modules.account.pojo.vo.authority.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.Role;
import in.hocg.eagle.modules.account.mapper.AuthorityMapper;
import in.hocg.eagle.modules.account.service.AuthorityService;
import in.hocg.eagle.modules.account.service.RoleAuthorityService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * [用户模块] 权限表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AuthorityServiceImpl extends AbstractServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    private final AuthorityMapping mapping;
    private final RoleAuthorityService roleAuthorityService;

    public boolean hasAuthorityCodeIgnoreId(String authorityCode, Long id) {
        return lambdaQuery().eq(Authority::getAuthorityCode, authorityCode)
            .ne(Objects.nonNull(id), Authority::getId, id)
            .count() > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(AuthorityInsertQo qo) {
        final Long parentId = qo.getParentId();
        StringBuilder path = new StringBuilder();

        // 如果存在父级别菜单
        if (Objects.nonNull(parentId)) {
            Authority parentAuthority = baseMapper.selectById(parentId);
            ValidUtils.notNull(parentAuthority, "父级不存在");
            path.append(parentAuthority.getTreePath());
            boolean parentIsOff = LangUtils.equals(Enabled.Off.getCode(), parentAuthority.getEnabled());
            boolean nowIsOn = LangUtils.equals(Enabled.On.getCode(), qo.getEnabled());
            ValidUtils.isFalse(parentIsOff && nowIsOn, "父级为禁用状态，子级不能为开启状态");
        }

        final Authority authority = mapping.asAuthority(qo);
        authority.setCreatedAt(qo.getCreatedAt());
        authority.setParentId(parentId);
        authority.setCreator(qo.getUserId());
        authority.setTreePath("/tmp");
        validInsert(authority);
        path.append(String.format("/%d", authority.getId()));
        authority.setTreePath(path.toString());
        validUpdateById(authority);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(AuthorityUpdateQo qo) {
        final Authority authority = mapping.asAuthority(qo);
        final Long parentId = authority.getParentId();
        final Integer enabled = qo.getEnabled();
        final Long id = qo.getId();

        // 如果需要更新父级
        if (Objects.nonNull(parentId)) {
            updateAuthorityTreePath(id, parentId);
        }

        // 如果需要更新启用状态
        if (Objects.nonNull(enabled)) {
            updateAuthorityEnabledStatus(id, enabled);
        }

        authority.setLastUpdatedAt(qo.getCreatedAt());
        authority.setLastUpdater(qo.getUserId());
        validUpdateById(authority);
    }

    @Override
    public List<Authority> search(AuthoritySearchQo qo) {
        return baseMapper.search(qo);
    }

    @Override
    public List<AuthorityTreeNodeVo> tree(AuthoritySearchQo qo) {
        final Long parentId = qo.getParentId();
        List<Authority> all = baseMapper.search(qo);
        return Tree.getChild(parentId, all.stream()
            .map(mapping::asAuthorityTreeNodeVo)
            .collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id, boolean force) {
        if (force) {
            deleteCurrentAndChildren(id);
        } else if (selectListChildrenById(id).isEmpty()) {
            deleteCurrentAndChildren(id);
        } else {
            throw ServiceException.wrap("该节点下含有子节点不能被删除");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(GrantRoleQo qo) {
        Long authorityId = qo.getId();
        List<Long> roles = qo.getRoles();
        roles.forEach(roleId -> roleAuthorityService.grantAuthority(roleId, authorityId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthorityComplexAndRoleVo selectOne(Long id) {
        final Authority entity = baseMapper.selectById(id);
        return convertComplexAndRole(entity);
    }

    private AuthorityComplexAndRoleVo convertComplexAndRole(Authority entity) {
        final Long id = entity.getId();
        List<Role> roles = roleAuthorityService.selectListRoleByAuthorityId(id);
        return mapping.asAuthorityComplexVo(entity, roles);
    }

    /**
     * 删除当前节点及其子节点
     *
     * @param id
     */
    private void deleteCurrentAndChildren(Long id) {
        final Authority authority = baseMapper.selectById(id);
        if (Objects.isNull(authority)) {
            return;
        }

        final String regexTreePath = String.format("%s.*?", authority.getTreePath());
        if (roleAuthorityService.isUsedAuthority(regexTreePath)) {
            throw ServiceException.wrap("所选节点正在被某些角色使用");
        }
        baseMapper.deleteListByRegexTreePath(regexTreePath);
    }

    /**
     * 获取该ID下所有孩子节点
     *
     * @param id
     * @return
     */
    private List<Authority> selectListChildrenById(Long id) {
        final Authority authority = baseMapper.selectById(id);
        if (Objects.isNull(authority)) {
            return Lists.newArrayList();
        }

        final String regexTreePath = String.format("%s/.*?", authority.getTreePath());
        return baseMapper.selectListByRegexTreePath(regexTreePath);
    }

    /**
     * - 父级更换，如果父级的父级发生更换，则其子级随着父级发生更换。
     */
    private void updateAuthorityTreePath(@NotNull Long id,
                                         Long parentId) {
        final Authority authority = baseMapper.selectById(id);
        if (LangUtils.equals(authority.getParentId(), parentId)) {
            return;
        }
        String oldTreePath = authority.getTreePath();

        String newParentTreePath = "";
        if (Objects.nonNull(parentId)) {
            final Authority parentAuthority = baseMapper.selectById(parentId);
            ValidUtils.notNull(parentAuthority, "父级不存在");
            newParentTreePath = parentAuthority.getTreePath();
        }
        String newTreePath = String.format("%s/%d", newParentTreePath, id);
        final String regexTreePath = String.format("%s.*?", oldTreePath);
        baseMapper.updateTreePathByRegexTreePath(regexTreePath, oldTreePath, newTreePath);

        Authority update = new Authority();
        update.setId(id);
        update.setParentId(parentId);
        update.setLastUpdatedAt(LocalDateTime.now());
        update.setLastUpdater(SecurityContext.getCurrentUserId());
        baseMapper.updateById(update);
    }

    /**
     * 状态更新
     * - 状态更新，如果父级为禁用状态，子权限不能更新为启用状态。
     * - 状态更新，如果父级更新为禁用状态，子权限均会更新为禁用状态。
     *
     * @param id
     * @param enabled
     */
    private void updateAuthorityEnabledStatus(@NotNull Long id,
                                              @NotNull Integer enabled) {
        final Authority authority = baseMapper.selectById(id);
        if (LangUtils.equals(authority.getEnabled(), enabled)) {
            return;
        }

        // 该权限的父节点
        final Long parentId = authority.getParentId();
        if (Objects.nonNull(parentId)) {
            Authority parentAuthority = baseMapper.selectById(parentId);
            ValidUtils.notNull(parentAuthority, "父级不存在");
            boolean parentIsOff = LangUtils.equals(Enabled.Off.getCode(), parentAuthority.getEnabled());
            boolean nowIsOn = LangUtils.equals(Enabled.On.getCode(), enabled);
            ValidUtils.isTrue(parentIsOff && nowIsOn, "父级为禁用状态，子级不能为开启状态");
        }

        // 该权限如果更新为禁用，则其子节点都需更新为禁用
        if (LangUtils.equals(Enabled.Off.getCode(), enabled)) {
            final String treePath = authority.getTreePath();
            final String regexTreePath = String.format("%s.*?", treePath);
            baseMapper.updateOffStatusByRegexTreePath(regexTreePath);
            return;
        }

        Authority update = new Authority();
        update.setId(id);
        update.setEnabled(enabled);
        update.setLastUpdatedAt(LocalDateTime.now());
        update.setLastUpdater(SecurityContext.getCurrentUserId());
        baseMapper.updateById(update);
    }

    @Override
    public void validEntity(Authority entity) {
        final Long id = entity.getId();
        final String authorityCode = entity.getAuthorityCode();
        if (Objects.nonNull(authorityCode)) {
            ValidUtils.isFalse(hasAuthorityCodeIgnoreId(authorityCode, id), "新增失败,权限码已经存在");
        }
    }
}
