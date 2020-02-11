package in.hocg.eagle.modules.account.service.impl;

import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.mapper.AuthorityMapper;
import in.hocg.eagle.modules.account.service.AuthorityService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

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

}
