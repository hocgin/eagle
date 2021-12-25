package in.hocg.eagle.manager;

import in.hocg.eagle.basic.AbstractSpringBootTest;
import in.hocg.eagle.basic.constant.message.TopicConstant;
import in.hocg.eagle.manager.lang.LangManager;
import in.hocg.eagle.manager.lang.dto.IpAndAddressDto;
import in.hocg.eagle.modules.com.entity.PersistenceMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hocgin on 2020/4/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class LangManagerTest extends AbstractSpringBootTest {
    @Autowired
    LangManager langManager;

    @Test
    public void getAddressByIp() {
        final IpAndAddressDto result = langManager.getAddressByIp("106.122.172.244");
        System.out.println(result.getAddress().orElse(""));
    }

    @Test
    public void test() {
        final PersistenceMessage message = new PersistenceMessage();
        message.setPayload("内容");
        message.setDestination(TopicConstant.Fields.TEST_TOPIC);
    }
}
