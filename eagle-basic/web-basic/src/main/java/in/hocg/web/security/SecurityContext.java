package in.hocg.web.security;

import in.hocg.web.SpringContext;
import in.hocg.web.exception.ServiceException;
import in.hocg.web.result.ResultCode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by hocgin on 2020/1/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@UtilityClass
public class SecurityContext {

    /**
     * 登录
     *
     * @param userId 用户ID
     */
    public void signin(String userId) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
    }

    /**
     * 获取授权方式
     *
     * @return
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 当前登录的用户
     *
     * @return
     */
    public Optional<String> getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth) || auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        try {
            return Optional.of(((String) auth.getPrincipal()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * 当前登录的用户
     *
     * @return
     */
    public Optional<User> getCurrentUser() {
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
     *
     * @param <T>
     * @return
     */
    public <T> T getCurrentUserId() {
        final Optional<User> userOptional = getCurrentUser();
        if (userOptional.isPresent()) {
            return (T) userOptional.get().getId();
        } else {
            throw ServiceException.wrap(ResultCode.AUTHENTICATION_ERROR.getMessage());
        }
    }

}
