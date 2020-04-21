package in.hocg.eagle.modules.wx.support;

import in.hocg.eagle.basic.Env;
import in.hocg.eagle.modules.wx.support.handle.ReplyHandle;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hocgin on 2020/4/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Configuration
public class WxMpStarter {

    @Bean
    @ConditionalOnMissingBean(WxMpConfigStorage.class)
    public WxMpConfigStorage mpConfigStorage() {
        final Env.Configs configs = Env.getConfigs();
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(configs.getWxMpAppId());
        configStorage.setSecret(configs.getWxMpSecret());
        configStorage.setToken(configs.getWxMpToken());
        configStorage.setAesKey(configs.getWxMpAesKey());
        return configStorage;
    }


    @Bean
    @ConditionalOnMissingBean(WxMpService.class)
    public WxMpService mpService(WxMpConfigStorage configStorage) {
        WxMpServiceImpl mpService = new WxMpServiceImpl();
        mpService.setWxMpConfigStorage(configStorage);
        return mpService;
    }

    @Bean
    @ConditionalOnMissingBean(WxMpMessageRouter.class)
    public WxMpMessageRouter mpMessageRouter(WxMpService mpService,
                                             ReplyHandle replyHandle) {
        return new WxMpMessageRouter(mpService)
            .rule().async(false)
            .content("芝麻开门")
            .handler(replyHandle).end();
    }

}
