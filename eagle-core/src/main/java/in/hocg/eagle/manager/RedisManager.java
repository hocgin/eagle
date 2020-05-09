package in.hocg.eagle.manager;

import com.alibaba.fastjson.JSON;
import in.hocg.eagle.manager.dto.IpAndAddressDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;
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
     * 获取地址
     *
     * @param ip
     * @return
     */
    public Optional<IpAndAddressDto> getIpAndAddress(String ip) {
        final ValueOperations<String, String> opsForValue = template.opsForValue();
        return serialize(opsForValue.get(ip), IpAndAddressDto.class);
    }

    /**
     * 缓存IP地址
     *
     * @param dto
     */
    public void setIpAndAddress(String ip, IpAndAddressDto dto) {
        final ValueOperations<String, String> opsForValue = template.opsForValue();
        opsForValue.set(ip, deserialize(dto), 30, TimeUnit.DAYS);
    }

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
     *
     * @param email
     * @param validToken
     * @return
     */
    public boolean validResetPasswordToken(@NonNull String email, @NonNull String validToken) {
        ValueOperations<String, String> opsForValue = template.opsForValue();
        final String token = opsForValue.get(email);
        return validToken.equals(token);
    }

    private String deserialize(Object object) {
        return JSON.toJSONString(object);
    }

    private <T> Optional<T> serialize(String text, Class<T> clazz) {
        if (Strings.isBlank(text)) {
            return Optional.empty();
        }
        return Optional.of(JSON.parseObject(text, clazz));
    }

    public void setUserId(String sessionId, Long userId) {
        final ValueOperations<String, String> opsForValue = template.opsForValue();
        opsForValue.set(sessionId, String.valueOf(userId), 5, TimeUnit.MINUTES);
    }

    public Optional<Long> getAndCleanUserId(String sessionId) {
        final ValueOperations<String, String> opsForValue = template.opsForValue();
        final String userId = opsForValue.get(sessionId);
        if (Strings.isNotBlank(userId)) {
            return Optional.ofNullable(Long.parseLong(userId));
        }
        return Optional.empty();
    }
}
