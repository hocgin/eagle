package in.hocg.eagle.basic.wx;

import in.hocg.eagle.basic.AbstractSpringBootTest;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Created by hocgin on 2020/4/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class WxTests extends AbstractSpringBootTest {
    public static String appid = "wx6c554c6d25b19c4a";

    @Autowired
    WxMpManager wxMpManager;

    @Test
    public void testGetMenus() {
        final Optional<WxMpMenu> wxMenus = wxMpManager.getWxMenus(appid);
        log.info("菜单: {}", wxMenus.orElse(null));
    }
}
