package in.hocg.eagle.modules.account.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.AuthorityMapping;
import in.hocg.eagle.mapstruct.qo.AuthorityPostQo;
import in.hocg.eagle.mapstruct.qo.AuthorityPutQo;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.mapper.AuthorityMapper;
import in.hocg.eagle.modules.account.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
    public void insertOne(AuthorityPostQo qo) {
        final Authority authority = mapping.asAuthority(qo);
        authority.setCreatedAt(qo.getCreatedAt());
        authority.setCreator(qo.getUserId());
        baseMapper.insert(authority);
    }
    
    @Override
    public void updateOne(AuthorityPutQo qo) {
    
    }
}
