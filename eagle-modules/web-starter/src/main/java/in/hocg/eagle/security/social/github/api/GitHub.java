package in.hocg.eagle.security.social.github.api;

import in.hocg.eagle.security.social.github.ApiBinding;
import in.hocg.eagle.security.social.github.GitHubUserInfo;

/**
 * Created by hocgin on 2020/5/9.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class GitHub extends ApiBinding {
    public static final String NAME = "github";

    public GitHub(String accessToken) {
        super(accessToken);
    }

    public GitHubUserInfo getUserInfo() {
        throw new UnsupportedOperationException();
    }
}
