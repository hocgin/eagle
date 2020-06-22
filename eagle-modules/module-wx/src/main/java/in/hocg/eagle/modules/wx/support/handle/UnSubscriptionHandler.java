package in.hocg.eagle.modules.wx.support.handle;

import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by hocgin on 2020/4/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class UnSubscriptionHandler extends AbstractHandler {
    private final WxMpManager wxMpManager;
    private final WxUserService wxUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        final String appId = wxMpService.getWxMpConfigStorage().getAppId();
        final String fromUser = wxMessage.getFromUser();
        wxUserService.unsubscribe(appId, fromUser);
        return null;
    }
}
