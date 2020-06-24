package in.hocg.basic.named;

import in.hocg.basic.named.service.NamedTestService;
import in.hocg.eagle.basic.AbstractSpringBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

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
//        Mockito.when(namedService.selectOneByDataDict("code", new String[]{"code"})).thenReturn("codeName");

//        final NamedTestVo namedVo = service.selectOneNamedVo("code");
//        Assert.notNull(namedVo.getCode(), "code");
//        Assert.notNull(namedVo.getCodeName(), "codeName");
    }
}
