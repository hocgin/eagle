package in.hocg.eagle.basic.ext.security.social.github;

import in.hocg.eagle.basic.ext.security.social.SocialUserInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by hocgin on 2020/5/9.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class GitHubUserInfo extends SocialUserInfo {

    public GitHubUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Serializable getId() {
        return attributes.get("id").toString();
    }
}
