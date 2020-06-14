package in.hocg.eagle.security.social.github;

import org.springframework.web.client.RestTemplate;

/**
 * Created by hocgin on 2020/5/9.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class ApiBinding {
    protected RestTemplate restTemplate;
    protected String accessToken;

    public ApiBinding(String accessToken) {
        this.accessToken = accessToken;
        this.restTemplate = new RestTemplate();
    }
}
