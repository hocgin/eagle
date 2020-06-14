package in.hocg.eagle.security.social;

import in.hocg.eagle.security.social.github.api.GitHub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Created by hocgin on 2020/5/9.
 * email: hocgin@gmail.com
 * 重定向URL: /login/oauth2/code/{registrationId}
 * 登录URL: /oauth2/authorization/{registrationId}
 *
 * @author hocgin
 */
@Configuration
public class SocialConfig {

    @Bean
    @RequestScope
    public GitHub gitHub(OAuth2AuthorizedClientService clientService) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = null;
        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            if (GitHub.NAME.equals(clientRegistrationId)) {
                OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
                accessToken = client.getAccessToken().getTokenValue();
            }
        }
        return new GitHub(accessToken);
    }

}
