package in.hocg.eagle.basic.ext.security.authentication.token;

import com.google.common.collect.Maps;
import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.basic.result.ResultCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AccountExpiredException;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by hocgin on 2020/1/9.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class TokenUtility {
    private static final HashMap<String, Object> EMPTY = Maps.newHashMap();
    private static final long EXPIRE_MILLIS = 1000 * 60 * 60 * 10L;
    private static final String KEY = "hocgin";

    public static String encode(String username) {
        final long currentTimeMillis = System.currentTimeMillis();
        final long expirationTimeMillis = currentTimeMillis + EXPIRE_MILLIS;
        final String token = Jwts.builder().setClaims(EMPTY).setSubject(username)
            .setIssuedAt(new Date(currentTimeMillis))
            .setExpiration(new Date(expirationTimeMillis))
            .signWith(SignatureAlgorithm.HS256, KEY).compact();
        final TokenManager tokenManager = SpringContext.getBean(TokenManager.class);
        tokenManager.putToken(username, token, EXPIRE_MILLIS);
        return token;
    }

    public static String decode(String token) {
        final String username = Jwts.parser().setSigningKey(KEY)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
        final TokenManager tokenManager = SpringContext.getBean(TokenManager.class);
        final String serverToken = tokenManager.getToken(username);
        if (token.equals(serverToken)) {
            return username;
        }
        throw new AccountExpiredException(ResultCode.AUTHENTICATION_ERROR.getMessage());
    }

}
