package in.hocg.eagle.basic.aspect.named.service;

import in.hocg.eagle.basic.aspect.named.InjectNamed;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
public class NamedTestService {
    
    @InjectNamed
    public NamedTestVo selectOneNamedVo(String code) {
        return new NamedTestVo()
                .setCode(code);
    }
}
