package in.hocg.eagle.modules.ums.service.impl;

import in.hocg.eagle.basic.AbstractSpringBootTest;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthoritySearchQo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.ums.service.AuthorityService;
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
        final List<AuthorityTreeNodeVo> search = authorityService.tree(new AuthoritySearchQo());
        log.debug("{}", search);
    }
}
