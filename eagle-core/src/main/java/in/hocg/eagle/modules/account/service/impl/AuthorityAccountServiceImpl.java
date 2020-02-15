package in.hocg.eagle.modules.account.service.impl;

import in.hocg.eagle.modules.account.entity.AuthorityAccount;
import in.hocg.eagle.modules.account.mapper.AuthorityAccountMapper;
import in.hocg.eagle.modules.account.service.AuthorityAccountService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [用户模块] 权限-账号表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AuthorityAccountServiceImpl extends AbstractServiceImpl<AuthorityAccountMapper, AuthorityAccount> implements AuthorityAccountService {
    
    @Override
    public boolean isUsedAuthority(String regexTreePath) {
        return baseMapper.selectListByAuthorityRegexTreePath(regexTreePath) > 0;
    }
}
