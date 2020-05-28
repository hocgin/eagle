package in.hocg.eagle.modules.wx.support.handle;

import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.constant.datadict.WxMatchMsgType;
import in.hocg.eagle.basic.constant.datadict.WxReplyMsgType;
import in.hocg.eagle.modules.wx.entity.WxMpReplyRule;
import in.hocg.eagle.modules.wx.service.WxMpReplyRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.builder.outxml.BaseBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 * 根据配置规则 自动进行回复
 *
 * @author hocgin
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RuleReplyHandler extends AbstractHandler {
    private final WxMpReplyRuleService replyRuleService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        final String appId = wxMpService.getWxMpConfigStorage().getAppId();
        final String msgType = wxMessage.getMsgType();
        final String event = wxMessage.getEvent();
        final String eventKey = wxMessage.getEventKey();
        final String content = wxMessage.getContent();

        final Optional<WxMatchMsgType> matchMsgTypeOpt = WxMatchMsgType.convert(msgType);

        // 如果回复规则不支持的消息类型
        if (!matchMsgTypeOpt.isPresent()) {
            return null;
        }

        final WxMatchMsgType wxMatchMsgType = matchMsgTypeOpt.get();
        final List<WxMpReplyRule> rules = replyRuleService.selectListByAppidAndMatchMsgTypeAndEnabledSortedDesc(appId, wxMatchMsgType, Enabled.On);
        final Optional<WxMpReplyRule> matchedRule = rules.parallelStream().filter(item -> wxMatchMsgType.match(item.getMatchRule(), event, eventKey, content)).findFirst();

        // 如果没有匹配到回复规则
        if (!matchedRule.isPresent()) {
            return null;
        }
        final WxMpReplyRule wxMpReplyRule = matchedRule.get();
        final Optional<WxReplyMsgType> replyMsgTypeOpt = IntEnum.of(wxMpReplyRule.getReplyMsgType(), WxReplyMsgType.class);

        // 如果没有对应的回复类型
        if (!replyMsgTypeOpt.isPresent()) {
            return null;
        }
        final WxReplyMsgType replyMsgType = replyMsgTypeOpt.get();
        final Optional<BaseBuilder<?, ? extends WxMpXmlOutMessage>> builderOpt = replyMsgType.asWxMpMessageBuilder(wxMpReplyRule.getReplyContent());

        // 如果没有封包
        if (!builderOpt.isPresent()) {
            return null;
        }
        final BaseBuilder<?, ? extends WxMpXmlOutMessage> message = builderOpt.get();
        message.fromUser(wxMessage.getToUser());
        message.toUser(wxMessage.getFromUser());
        return message.build();
    }
}
