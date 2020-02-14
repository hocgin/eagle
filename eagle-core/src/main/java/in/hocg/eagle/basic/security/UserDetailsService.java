package in.hocg.eagle.basic.security;

import com.google.common.collect.Lists;
import in.hocg.eagle.modules.account.entity.Account;
import in.hocg.eagle.modules.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final AccountService accountService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Account> accountOptional = accountService.selectOneByUsername(username);
        if (accountOptional.isPresent()) {
            final Account account = accountOptional.get();
            return new User(account.getId(), username, account.getPassword(), Lists.newArrayList());
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
