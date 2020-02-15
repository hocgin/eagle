package in.hocg.eagle.modules.account.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.Tree;
import in.hocg.eagle.basic.constant.Enabled;
import in.hocg.eagle.basic.security.SecurityContext;
import in.hocg.eagle.mapstruct.AuthorityMapping;
import in.hocg.eagle.mapstruct.qo.AuthorityPostQo;
import in.hocg.eagle.mapstruct.qo.AuthorityPutQo;
import in.hocg.eagle.mapstruct.qo.AuthoritySearchQo;
import in.hocg.eagle.mapstruct.vo.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.mapper.AuthorityMapper;
import in.hocg.eagle.modules.account.service.AuthorityService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.VerifyUtils;
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
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(AuthorityPostQo qo) {
        final Integer parentId = qo.getParentId();
        StringBuilder path = new StringBuilder();
        
        // 如果存在父级别菜单
        if (Objects.nonNull(parentId)) {
            Authority parentAuthority = baseMapper.selectById(parentId);
            VerifyUtils.notNull(parentAuthority, "父级标签不存在");
            path.append(parentAuthority.getTreePath());
            boolean parentIsOff = LangUtils.equals(Enabled.Off.getCode(), parentAuthority.getEnabled());
            boolean nowIsOn = LangUtils.equals(Enabled.On.getCode(), qo.getEnabled());
            VerifyUtils.isTrue(parentIsOff && nowIsOn, "父级为禁用状态，子级不能为开启状态");
        }
        
        final Authority authority = mapping.asAuthority(qo);
        authority.setCreatedAt(qo.getCreatedAt());
        authority.setParentId(parentId);
        authority.setCreator(qo.getUserId());
        authority.setTreePath("/tmp");
        baseMapper.insert(authority);
        path.append(String.format("/%d", authority.getId()));
        authority.setTreePath(path.toString());
        baseMapper.updateById(authority);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(AuthorityPutQo qo) {
        final Authority authority = mapping.asAuthority(qo);
        final Integer parentId = authority.getParentId();
        final Integer enabled = qo.getEnabled();
        final Integer id = qo.getId();
        
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
        baseMapper.updateById(authority);
    }
    
    @Override
    public List<AuthorityTreeNodeVo> search(AuthoritySearchQo qo) {
        List<Authority> all = baseMapper.selectListByParentId(qo.getParentId());
        return Tree.getChild(null, all.stream()
                .map(mapping::asAuthorityTreeNodeVo)
                .collect(Collectors.toList()));
    }
    
    /**
     * - 父级更换，如果父级的父级发生更换，则其子级随着父级发生更换。
     */
    private void updateAuthorityTreePath(@NotNull Integer id,
                                         Integer parentId) {
        final Authority authority = baseMapper.selectById(id);
        if (LangUtils.equals(authority.getParentId(), parentId)) {
            return;
        }
        String oldTreePath = authority.getTreePath();
        
        String newParentTreePath = "";
        if (Objects.nonNull(parentId)) {
            final Authority parentAuthority = baseMapper.selectById(parentId);
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
    private void updateAuthorityEnabledStatus(@NotNull Integer id,
                                              @NotNull Integer enabled) {
        final Authority authority = baseMapper.selectById(id);
        if (LangUtils.equals(authority.getEnabled(), enabled)) {
            return;
        }
        
        // 该权限的父节点
        final Integer parentId = authority.getParentId();
        if (Objects.nonNull(parentId)) {
            Authority parentAuthority = baseMapper.selectById(parentId);
            boolean parentIsOff = LangUtils.equals(Enabled.Off.getCode(), parentAuthority.getEnabled());
            boolean nowIsOn = LangUtils.equals(Enabled.On.getCode(), enabled);
            VerifyUtils.isTrue(parentIsOff && nowIsOn, "父级为禁用状态，子级不能为开启状态");
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
}
