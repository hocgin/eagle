package in.hocg.eagle.manager.sms;

import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.manager.redis.RedisManager;
import in.hocg.eagle.utils.string.TextBlock;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by hocgin on 2020/3/27.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SmsManager {
    private final RedisManager redisManager;

    private void sendSms(String phone, String text) {
        log.info("发送短信, 接收人: [{}], 内容: [{}]", phone, text);
    }

    public void sendSmsCode(String phone, String code) {
        if (Boolean.TRUE.compareTo(redisManager.exitsSmsCode(phone)) == 0) {
            throw ServiceException.wrap("稍后获取验证码");
        }
        redisManager.setSmsCode(phone, code);
        this.sendSms(phone, TextBlock.format("短信验证码: {}", code));
    }

    public boolean validSmsCode(@NonNull String phone, @NonNull String smsCode) {
        return redisManager.validSmsCode(phone, smsCode);
    }
}
