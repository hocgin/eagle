package in.hocg.eagle.basic.security;

import in.hocg.eagle.utils.RequestUtility;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hocgin on 2020/1/9.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class IsAjaxRequestMatcher implements RequestMatcher {
    
    @Override
    public boolean matches(HttpServletRequest request) {
        return RequestUtility.isAJAX(request);
    }
    
}
