package in.hocg.eagle.modules.ums.service.impl;

import in.hocg.eagle.basic.AbstractSpringBootTest;
import in.hocg.eagle.modules.ums.service.AccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hocgin on 2020/4/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class AccountServiceImplTest extends AbstractSpringBootTest {

    @Autowired
    AccountService service;

    @Test
    public void resetPassword() {
        service.resetPassword(1L);
    }
}
