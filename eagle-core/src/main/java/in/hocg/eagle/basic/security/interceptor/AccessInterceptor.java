package in.hocg.eagle.basic.security.interceptor;

import in.hocg.eagle.basic.constant.GlobalConstant;
import in.hocg.eagle.basic.security.GrantedAuthority;
import in.hocg.eagle.basic.security.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class AccessInterceptor extends OncePerRequestFilter {
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final Authentication authentication = SecurityContext.getAuthentication();
        final String method = request.getMethod();
        final String uri = request.getRequestURI();
        if (access(authentication, method, uri)) {
            filterChain.doFilter(request, response);
        }
        log.error("拒绝访问");
    }
    
    
    private boolean access(Authentication authentication, String method, String uri) {
        // 如果是匿名访问
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        
        if (Objects.isNull(authentication.getPrincipal())) {
            return false;
        }
        
        return matchAuthorities(authentication, method, uri);
    }
    
    private boolean matchAuthorities(Authentication authentication, String method, String uri) {
        if (GlobalConstant.SUPPER_ADMIN_USERNAME.equals(authentication.getName())) {
            return true;
        }
        final Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        String authorityUri;
        String authorityMethod;
        boolean permitMethod;
        boolean permitUri;
        for (GrantedAuthority authority : authorities) {
            authorityMethod = authority.getMethod();
            authorityUri = authority.getUri();
            permitMethod = "*".equalsIgnoreCase(authorityMethod) || method.equalsIgnoreCase(authorityMethod);
            permitUri = ANT_PATH_MATCHER.match(authorityUri, uri);
            if (permitMethod && permitUri) {
                return true;
            }
        }
        
        return false;
    }
}
