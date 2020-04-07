package in.hocg.eagle.manager;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by hocgin on 2020/4/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RedisManager {
    private final StringRedisTemplate template;

    /**
     * 设置重置密码 Token
     *
     * @param email
     * @param token
     * @param expiredMinutes
     */
    public void setResetPasswordToken(@NonNull String email, @NonNull String token, long expiredMinutes) {
        ValueOperations<String, String> opsForValue = template.opsForValue();
        opsForValue.set(email, token, expiredMinutes, TimeUnit.MINUTES);
        log.debug("验证码设置[邮箱: {}, Token: {}]", email, token);
    }

    /**
     * 验证重置密码 Token
     * @param email
     * @param validToken
     * @return
     */
    public boolean validResetPasswordToken(@NonNull String email, @NonNull String validToken) {
        ValueOperations<String, String> opsForValue = template.opsForValue();
        final String token = opsForValue.get(email);
        return validToken.equals(token);
    }
}
