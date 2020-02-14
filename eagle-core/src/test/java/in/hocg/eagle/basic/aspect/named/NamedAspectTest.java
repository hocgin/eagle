package in.hocg.eagle.basic.aspect.named;

import in.hocg.eagle.basic.AbstractSpringBootTest;
import in.hocg.eagle.basic.aspect.named.service.NamedTestService;
import in.hocg.eagle.basic.aspect.named.service.NamedTestVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import static org.mockito.Mockito.when;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class NamedAspectTest extends AbstractSpringBootTest {
    @Autowired
    private NamedTestService service;
    @MockBean
    private NamedService namedService;
    
    
    @Test
    public void testAround() {
        when(namedService.selectOneByTypeAndId("type", "code")).thenReturn("codeName");
        
        final NamedTestVo namedVo = service.selectOneNamedVo("code");
        Assert.notNull(namedVo.getCode(), "code");
        Assert.notNull(namedVo.getCodeName(), "codeName");
    }
}