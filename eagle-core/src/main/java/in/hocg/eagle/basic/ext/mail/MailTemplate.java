package in.hocg.eagle.basic.ext.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by hocgin on 2018/10/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class MailTemplate {
    private String username;
    private String personal;

    private JavaMailSender mailSender;

    public MailTemplate(String username, String personal, JavaMailSender mailSender) {
        this.username = username;
        this.personal = personal;
        this.mailSender = mailSender;
    }

    /**
     * 邮件发送
     *
     * @param to         收件人
     * @param subject    标题
     * @param text       内容
     * @param inline     图片
     * @param attachment 附件
     */
    public void send(@NotNull List<String> to, @NotNull String subject, @NotNull String text, Map<String, File> inline, Map<String, File> attachment) {
        if (to.isEmpty()) {
            return;
        }

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(to.toArray(new String[]{}));
            messageHelper.setFrom(username, personal);

            // - 给自己邮箱抄录一份
            messageHelper.setCc(username);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);

            // 图片
            if (Objects.nonNull(inline)) {
                for (Map.Entry<String, File> entry : inline.entrySet()) {
                    final String key = entry.getKey();
                    final File value = entry.getValue();
                    messageHelper.addInline(key, value);
                }
            }

            // 附件
            if (Objects.nonNull(attachment)) {
                for (Map.Entry<String, File> entry : attachment.entrySet()) {
                    String key = entry.getKey();
                    final File value = entry.getValue();
                    messageHelper.addAttachment(key, value);
                }
            }
            mailSender.send(messageHelper.getMimeMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }
    }

    public void send(List<String> to, String subject, String text) {
        send(to, subject, text, null, null);
    }

}
