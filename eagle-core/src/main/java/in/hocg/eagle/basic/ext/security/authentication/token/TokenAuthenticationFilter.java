package in.hocg.eagle.basic.ext.security.authentication.token;

import in.hocg.eagle.basic.ext.web.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hocgin on 2020/1/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private static final String TOKEN_HEADER = "Token";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(TOKEN_HEADER);

        if (Strings.isBlank(authorizationHeader) || !authorizationHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authorization = authorizationHeader.substring(BEARER.length());

        final SecurityContext context = SecurityContextHolder.getContext();

        // 如果需要使用缓存的话，取消注释。这时候权限和角色的变更需要等待缓存过期
//        if (context.getAuthentication() != null) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        try {
            String username = TokenUtility.decode(authorization);
            final UserDetailsService userDetailsService = SpringContext.getBean(UserDetailsService.class);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(token);
        } catch (Exception ignored) {
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
