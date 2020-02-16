package in.hocg.eagle.basic.security;

import in.hocg.eagle.mapstruct.AllInMapping;
import in.hocg.eagle.modules.account.entity.Account;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
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
    private final AllInMapping mapping;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Account> accountOptional = accountService.selectOneByUsername(username);
        if (accountOptional.isPresent()) {
            final Account account = accountOptional.get();
            List<Authority> authorities = accountService.selectListAuthorityById(account.getId());
            final List<GrantedAuthority> grantedAuthorities = mapping.asGrantedAuthority(authorities);
            return new User(account.getId(), username, account.getPassword(), grantedAuthorities);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
