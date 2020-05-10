package in.hocg.eagle.basic.ext.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Created by hocgin on 2020/4/5.
 * email: hocgin@gmail.com
 * 邮件相关
 *
 * @author hocgin
 */
@Configuration
public class MailConfig {

    @Bean
    public MailTemplate mailTemplate(@Value("${spring.mail.username}") String username,
                                     @Value("${spring.mail.personal}") String personal,
                                     JavaMailSender javaMailSender) {
        return new MailTemplate(username, personal, javaMailSender);
    }
}
