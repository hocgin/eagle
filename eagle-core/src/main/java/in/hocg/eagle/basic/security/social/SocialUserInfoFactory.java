package in.hocg.eagle.basic.security.social;

import in.hocg.eagle.basic.security.social.github.GitHubUserInfo;
import in.hocg.eagle.basic.security.social.github.api.GitHub;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * Created by hocgin on 2020/5/10.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class SocialUserInfoFactory {

    public SocialUserInfo getSocialUserInfo(String registrationId, Map<String, Object> attributes) {
        if (GitHub.NAME.equalsIgnoreCase(registrationId)) {
            return new GitHubUserInfo(attributes);
        } else {
            throw new UnsupportedOperationException("Sorry! " + registrationId + " is not supported yet.");
        }
    }
}
