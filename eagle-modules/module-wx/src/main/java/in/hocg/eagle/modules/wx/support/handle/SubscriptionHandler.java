package in.hocg.eagle.modules.wx.support.handle;

import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Created by hocgin on 2020/4/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SubscriptionHandler extends AbstractHandler {
    private final WxMpManager wxMpManager;
    private final WxUserService wxUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        final String appId = wxMpService.getWxMpConfigStorage().getAppId();
        final String fromUser = wxMessage.getFromUser();
        final Optional<WxUser> wxUser = wxMpManager.getWxUser(appId, fromUser);
        wxUser.ifPresent(wxUserService::saveOrUpdate);
        return null;
    }
}
