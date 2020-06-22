package in.hocg.eagle.manager;

import in.hocg.eagle.basic.AbstractSpringBootTest;
import in.hocg.web.manager.LangManager;
import in.hocg.web.manager.dto.IpAndAddressDto;
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
}
