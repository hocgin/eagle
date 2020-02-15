package in.hocg.eagle.basic.security;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.result.ResultCode;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * Created by hocgin on 2020/1/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class SecurityContext {
    
    /**
     * 登录
     *
     * @param userId 用户ID
     */
    public static void signin(String userId) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
    }
    
    /**
     * 当前登录的用户
     *
     * @return
     */
    public static Optional<String> getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        return Optional.of(((String) auth.getPrincipal()));
    }
    
    /**
     * 当前登录的用户
     *
     * @return
     */
    public static Optional<User> getCurrentUser() {
        final Optional<String> usernameOptional = getCurrentUsername();
        if (usernameOptional.isPresent()) {
            final String username = usernameOptional.get();
            final org.springframework.security.core.userdetails.UserDetailsService userDetailsService = SpringContext.getBean(UserDetailsService.class);
            return Optional.of(((User) userDetailsService.loadUserByUsername(username)));
        }
        return Optional.empty();
    }
    
    /**
     * User Id
     * @param <T>
     * @return
     */
    public <T> T getCurrentUserId() {
        final Optional<User> userOptional = getCurrentUser();
        if (userOptional.isPresent()) {
            return (T) userOptional.get().getId();
        } else {
            throw ServiceException.wrap(ResultCode.ACCESS_DENIED_ERROR.getMessage());
        }
    }
    
}
