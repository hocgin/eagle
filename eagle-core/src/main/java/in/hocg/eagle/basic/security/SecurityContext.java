package in.hocg.eagle.basic.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

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
}
