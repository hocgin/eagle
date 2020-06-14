package in.hocg.eagle.security.social;

import in.hocg.web.SpringContext;
import in.hocg.web.security.User;
import in.hocg.web.security.TokenUtility;
import in.hocg.web.manager.redis.RedisManager;
import in.hocg.web.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by hocgin on 2020/5/9.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */

@Controller
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SocialEndpoint {
    private final RedisManager redisManager;

    @RequestMapping("/oauth2/authorization/{registrationId}/{authorization}")
    public String authorization(@PathVariable("registrationId") String registrationId,
                                @PathVariable(value = "authorization", required = false) String authorization) {
        final HttpSession session = SpringContext.getSession().orElseThrow(() -> new SecurityException("获取会话失败"));
        if (Objects.nonNull(authorization)) {
            String username = TokenUtility.decode(authorization);
            ValidUtils.notNull(username, "会话过期");
            if (Objects.nonNull(username)) {
                final UserDetailsService userDetailsService = SpringContext.getBean(UserDetailsService.class);
                final User userDetails = (User) userDetailsService.loadUserByUsername(username);
                if (Objects.nonNull(userDetails)) {
                    redisManager.setUserId(session.getId(), userDetails.getId());
                }
            }
        }
        return "redirect:" + OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + registrationId;
    }

}
