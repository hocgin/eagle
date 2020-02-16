package in.hocg.eagle.basic.security;

import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by hocgin on 2017/10/26.
 * email: hocgin@gmail.com
 */
//@Component
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {
    
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        return authorities.parallelStream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(s -> s.equalsIgnoreCase((String) permission));
    }
    
    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId,
                                 String targetType,
                                 Object permission) {
        return false;
    }
}
