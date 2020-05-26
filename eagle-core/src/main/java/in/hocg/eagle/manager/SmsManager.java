package in.hocg.eagle.manager;

import in.hocg.eagle.utils.string.TextBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by hocgin on 2020/3/27.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Component
public class SmsManager {

    private void sendSms(String phone, String text) {
        log.info("发送短信, 接收人: [{}], 内容: [{}]", phone, text);
    }

    public void sendSmsCode(String phone, String code) {
        this.sendSms(phone, TextBlock.format("短信验证码: {}", code));
    }
}
