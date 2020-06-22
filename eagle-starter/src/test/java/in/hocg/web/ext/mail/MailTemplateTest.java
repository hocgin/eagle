package in.hocg.web.ext.mail;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractSpringBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hocgin on 2020/4/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class MailTemplateTest extends AbstractSpringBootTest {

    @Autowired
    MailTemplate mailTemplate;

    @Test
    public void send() {
        mailTemplate.send(Lists.newArrayList("hocgin@gmail.com"), "这是一个标题", "邮件内容📧");
    }
}
