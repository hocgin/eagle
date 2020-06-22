package in.hocg.eagle.basic.security;

import com.google.common.collect.Lists;
import in.hocg.eagle.modules.ums.entity.Account;
import in.hocg.eagle.modules.ums.entity.Authority;
import in.hocg.eagle.modules.ums.entity.Role;
import in.hocg.eagle.modules.ums.mapstruct.AuthorityMapping;
import in.hocg.eagle.modules.ums.mapstruct.RoleMapping;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.web.constant.GlobalConstant;
import in.hocg.web.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final RoleMapping roleMapping;
    private final AuthorityMapping authorityMapping;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Account> accountOptional = accountService.selectOneByUsername(username);
        if (accountOptional.isPresent()) {
            final Account account = accountOptional.get();
            final Long id = account.getId();
            final String password = account.getPassword();
            final List<Role> roles = accountService.selectListRoleById(id, GlobalConstant.CURRENT_PLATFORM.getCode());
            final List<Authority> authorities = accountService.selectListAuthorityById(id, GlobalConstant.CURRENT_PLATFORM.getCode());
            return new User(id, username, password, buildAuthorities(roles, authorities));
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    /**
     * 构建权限集
     *
     * @param roles
     * @param authorities
     * @return
     */
    private List<GrantedAuthority> buildAuthorities(List<Role> roles, List<Authority> authorities) {
        final List<GrantedAuthority> roleAuthorities = this.asGrantedAuthorityWithRoles(roles);
        final List<GrantedAuthority> grantedAuthorities = this.asGrantedAuthorityWithAuthorities(authorities);
        final ArrayList<GrantedAuthority> result = Lists.newArrayList();
        result.addAll(roleAuthorities);
        result.addAll(grantedAuthorities);
        return result;
    }

    private List<GrantedAuthority> asGrantedAuthorityWithRoles(List<Role> roles) {
        return roles.parallelStream().map(role -> new GrantedAuthority()
            .setAuthority("ROLE_" + role.getRoleCode())).collect(Collectors.toList());
    }

    private List<GrantedAuthority> asGrantedAuthorityWithAuthorities(List<Authority> roles) {
        return roles.parallelStream().map(authority -> new GrantedAuthority().setAuthority(authority.getAuthorityCode())).collect(Collectors.toList());
    }
}
