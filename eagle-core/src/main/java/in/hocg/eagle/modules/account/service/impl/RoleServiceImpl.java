package in.hocg.eagle.modules.account.service.impl;

import in.hocg.eagle.modules.account.entity.Role;
import in.hocg.eagle.modules.account.mapper.RoleMapper;
import in.hocg.eagle.modules.account.service.RoleService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

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

}
