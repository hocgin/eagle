package in.hocg.eagle.modules.wx.support.handle;

import in.hocg.eagle.basic.env.Env;
import in.hocg.eagle.utils.LangUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by hocgin on 2020/4/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
public class DebugReplyHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String msg;
        try {
            msg = new StringJoiner("\n")
                .add(String.format("现在时间: %s", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)))
                .add("当前环境: " + Arrays.toString(Env.getActiveProfiles()))
                .add(String.format("Open-Id: %s", wxMessage.getFromUser()))
                .add(String.format("当前服务器 IP: %s", LangUtils.getIpv4Addresses()))
                .toString();
        } catch (Exception e) {
            msg = e.getMessage();
        }
        return new TextBuilder()
            .fromUser(wxMessage.getToUser())
            .toUser(wxMessage.getFromUser())
            .content(msg)
            .build();
    }
}
