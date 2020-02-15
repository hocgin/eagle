package in.hocg.eagle.modules.account.service.impl;

import in.hocg.eagle.modules.account.entity.RoleAuthority;
import in.hocg.eagle.modules.account.mapper.RoleAuthorityMapper;
import in.hocg.eagle.modules.account.service.RoleAuthorityService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

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
public class RoleAuthorityServiceImpl extends AbstractServiceImpl<RoleAuthorityMapper, RoleAuthority> implements RoleAuthorityService {
    
    @Override
    public boolean isUsedAuthority(String regexTreePath) {
        return baseMapper.selectListByAuthorityRegexTreePath(regexTreePath) > 0;
    }
}
