package in.hocg.eagle.basic.ext.security.authentication.token;

/**
 * Created by hocgin on 2020/7/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface TokenManager {

    void putToken(String username, String token, long expireMillis);

    String getToken(String username);
}
