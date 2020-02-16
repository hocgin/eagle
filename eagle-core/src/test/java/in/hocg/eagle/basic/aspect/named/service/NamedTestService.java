package in.hocg.eagle.basic.aspect.named.service;

import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
public class NamedTestService {
    
    public NamedTestVo selectOneNamedVo(String code) {
        return new NamedTestVo()
                .setCode(code);
    }
}
