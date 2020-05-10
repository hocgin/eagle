package in.hocg.eagle.modules.wx.support;

import in.hocg.eagle.modules.wx.support.handle.ReplyHandler;
import in.hocg.eagle.modules.wx.support.handle.SubscriptionHandler;
import in.hocg.eagle.modules.wx.support.handle.UnSubscriptionHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
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
    @ConditionalOnMissingBean(WxMpService.class)
    public WxMpService mpService() {
        return new WxMpServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(WxMpMessageRouter.class)
    public WxMpMessageRouter mpMessageRouter(WxMpService mpService,
                                             SubscriptionHandler subscriptionHandler,
                                             UnSubscriptionHandler unSubscriptionHandler,
                                             ReplyHandler replyHandler) {
        return new WxMpMessageRouter(mpService)
            // 关注事件
            .rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(subscriptionHandler).next()
            // 取消关注事件
            .rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE).handler(unSubscriptionHandler).next()
            .rule().async(false).content("芝麻开门").handler(replyHandler)
            .end();
    }

}
