package in.hocg.web.constant;

import lombok.experimental.UtilityClass;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class AuthorizeConstant {
    public final String IS_ANONYMOUS = "isAnonymous()";
    public final String IS_AUTHENTICATED = "isAuthenticated()";
    public final String IS_MANAGER = "hasRole('EAGLE')";
    public final String IS_MINI_EAGLE = "hasRole('MINI_EAGLE')";
}
