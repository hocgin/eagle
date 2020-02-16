package in.hocg.eagle.modules.account.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.account.entity.Account;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.AuthorityAccount;
import in.hocg.eagle.modules.account.mapper.AuthorityAccountMapper;
import in.hocg.eagle.modules.account.service.AccountService;
import in.hocg.eagle.modules.account.service.AuthorityAccountService;
import in.hocg.eagle.modules.account.service.AuthorityService;
import in.hocg.eagle.utils.VerifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    private final AccountService accountService;
    private final AuthorityService authorityService;
    
    @Override
    public boolean isUsedAuthority(String regexTreePath) {
        return baseMapper.selectListByAuthorityRegexTreePath(regexTreePath) > 0;
    }
    
    @Override
    public void grantAuthority(Integer accountId, Integer authorityId) {
        final Account account = accountService.getById(accountId);
        VerifyUtils.notNull(account, "授权失败");
        final Authority authority = authorityService.getById(authorityId);
        VerifyUtils.notNull(authority, "授权失败");
        if (isHasAuthority(accountId, authorityId)) {
            return;
        }
        final AuthorityAccount insert = new AuthorityAccount()
                .setAccountId(accountId)
                .setAuthorityId(authorityId);
        baseMapper.insert(insert);
    }
    
    @Override
    public Collection<? extends Authority> selectListAuthorityByAccountId(Integer accountId) {
        return baseMapper.selectListAuthorityByAccountId(accountId);
    }
    
    /**
     * 检查账号是否已经具备该权限
     *
     * @param accountId
     * @param authorityId
     * @return
     */
    private boolean isHasAuthority(Integer accountId, Integer authorityId) {
        final LambdaQueryChainWrapper<AuthorityAccount> where = lambdaQuery()
                .eq(AuthorityAccount::getAccountId, accountId)
                .eq(AuthorityAccount::getAuthorityId, authorityId);
        return baseMapper.selectCount(where) > 0;
    }
}
