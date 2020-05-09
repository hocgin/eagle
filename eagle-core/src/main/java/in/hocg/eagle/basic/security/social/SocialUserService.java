package in.hocg.eagle.basic.security.social;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.manager.RedisManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Created by hocgin on 2020/5/9.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SocialUserService extends DefaultOAuth2UserService {
    private final RedisManager redisManager;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    /**
     * 处理社交账号登录的用户信息
     *
     * @param userRequest
     * @param oAuth2User
     * @return
     */
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        final HttpSession session = SpringContext.getSession().orElseThrow(() -> new SecurityException("获取会话失败"));
        final Optional<Long> userIdOpt = redisManager.getAndCleanUserId(session.getId());
        log.info("当前登录的用户:: " + userIdOpt.orElse(null));
        log.info("社交登录的用户:: " + oAuth2User);

        // 1. 如果已经登录 => 绑定账号
        if (userIdOpt.isPresent()) {
        }
        // 2. 如果未登录
        else {
            // 2.1 如果有绑定账号 => 登录完成
            // 2.2 如果没有绑定账号 => 注册+绑定页面
        }
        return oAuth2User;
    }
}
