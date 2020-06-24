package in.hocg.basic.named.service;

import in.hocg.web.aspect.logger.UseLogger;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
public class NamedTestService {

    @UseLogger("测试")
    public NamedTestVo selectOneNamedVo(String code) {
        return new NamedTestVo()
                .setCode(code);
    }
}
