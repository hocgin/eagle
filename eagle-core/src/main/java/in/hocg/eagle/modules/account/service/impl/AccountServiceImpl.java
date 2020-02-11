package in.hocg.eagle.modules.account.service.impl;

import in.hocg.eagle.modules.account.entity.Account;
import in.hocg.eagle.modules.account.mapper.AccountMapper;
import in.hocg.eagle.modules.account.service.AccountService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [用户模块] 账号表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountServiceImpl extends AbstractServiceImpl<AccountMapper, Account> implements AccountService {

}
