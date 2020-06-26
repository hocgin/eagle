package in.hocg.eagle.modules.ums.service.impl;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.datastruct.tree.Tree;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.ext.mybatis.tree.TreeServiceImpl;
import in.hocg.eagle.modules.ums.mapstruct.AuthorityMapping;
import in.hocg.eagle.modules.ums.entity.Authority;
import in.hocg.eagle.modules.ums.entity.Role;
import in.hocg.eagle.modules.ums.mapper.AuthorityMapper;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthorityInsertQo;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthoritySearchQo;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthorityUpdateQo;
import in.hocg.eagle.modules.ums.pojo.qo.role.GrantRoleQo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityComplexAndRoleVo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.ums.service.AuthorityService;
import in.hocg.eagle.modules.ums.service.RoleAuthorityService;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class AuthorityServiceImpl extends TreeServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

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
        final Authority authority = mapping.asAuthority(qo);
        authority.setCreatedAt(qo.getCreatedAt());
        authority.setCreator(qo.getUserId());
        validUpdateById(authority);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(AuthorityUpdateQo qo) {
        final Authority authority = mapping.asAuthority(qo);
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
        super.deleteCurrentAndChildren(id);
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


    @Override
    public void validEntity(Authority entity) {
        super.validEntity(entity);
        final Long id = entity.getId();
        final String authorityCode = entity.getAuthorityCode();
        if (Objects.nonNull(authorityCode)) {
            ValidUtils.isFalse(hasAuthorityCodeIgnoreId(authorityCode, id), "新增失败,权限码已经存在");
        }
    }
}
