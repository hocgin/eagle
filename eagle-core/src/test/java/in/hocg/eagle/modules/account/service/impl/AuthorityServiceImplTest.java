package in.hocg.eagle.modules.account.service.impl;

import in.hocg.eagle.basic.AbstractSpringBootTest;
import in.hocg.eagle.mapstruct.qo.AuthoritySearchQo;
import in.hocg.eagle.mapstruct.vo.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.account.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class AuthorityServiceImplTest extends AbstractSpringBootTest {
    
    @Autowired
    AuthorityService authorityService;
    
    @Test
    public void search() {
        final List<AuthorityTreeNodeVo> search = authorityService.search(new AuthoritySearchQo());
        log.debug("{}", search);
    }
}