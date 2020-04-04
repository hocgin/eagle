package in.hocg.eagle.modules.com.service.impl;

import in.hocg.eagle.basic.AbstractSpringBootTest;
import in.hocg.eagle.modules.com.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hocgin on 2020/4/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class ShortUrlServiceImplTest extends AbstractSpringBootTest {
    @Autowired
    ShortUrlService service;

    @Test
    public void getOrCreateShortUrlCodeUseRetry() {
        final String code = service.getOrCreateShortUrlCodeUseRetry("http://www.baidu.com/sd", 3);
        log.info("短链码: {}", code);
    }
}
