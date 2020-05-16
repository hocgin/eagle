package in.hocg.eagle.basic.constant.datadict.wx.reply;

import in.hocg.eagle.basic.constant.datadict.IntJSONEnum;
import in.hocg.eagle.basic.constant.datadict.Valid;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import me.chanjar.weixin.common.api.WxConsts;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by hocgin on 2020/5/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("匹配消息类型")
@RequiredArgsConstructor
public enum WxMatchMsgType implements IntJSONEnum {
    Text(0, "文本", Text.class),
    Event(1, "事件", Event.class);
    private final Integer code;
    private final String name;
    private final Class<? extends Valid> clazz;
    public static final String KEY = "wxMatchMsgType";

    public static Optional<WxMatchMsgType> convert(String msgType) {
        WxMatchMsgType result = null;
        switch (msgType) {
            case WxConsts.XmlMsgType.EVENT: {
                result = WxMatchMsgType.Event;
                break;
            }
            case WxConsts.XmlMsgType.TEXT: {
                result = WxMatchMsgType.Text;
                break;
            }
            default:
        }
        return Optional.ofNullable(result);
    }

    public <T extends Valid> T asObject(String json) {
        return (T) this.asClass(json, this.getClazz());
    }

    public boolean match(String ruleJson, String event, String eventKey, String content) {
        final Object ruleObject = asObject(ruleJson);
        boolean isMatched = false;
        if (ruleObject instanceof Text) {
            final WxMatchMsgType.Text rule = (WxMatchMsgType.Text) ruleObject;
            final String ruleContent = rule.getContent();
            final String ruleRContent = rule.getRContent();
            if (Objects.nonNull(ruleContent)) {
                isMatched = LangUtils.equals(content, ruleContent);
            } else if (Objects.nonNull(ruleRContent)) {
                isMatched = Pattern.compile(ruleRContent).matcher(content).find();
            }
        } else if (ruleObject instanceof Event) {
            final WxMatchMsgType.Event rule = (WxMatchMsgType.Event) ruleObject;
            final String ruleEvent = rule.getEvent();
            final String ruleEventKey = rule.getEventKey();

            if (Objects.nonNull(ruleEvent)) {
                isMatched = LangUtils.equals(ruleEvent, event);
            }
            if (isMatched && Objects.nonNull(ruleEventKey)) {
                isMatched = LangUtils.equals(ruleEventKey, eventKey);
            }
        }
        return isMatched;
    }

    @Data
    @Accessors(chain = true)
    @ApiModel("匹配消息类型::文本")
    public static class Text implements Valid {
        @ApiModelProperty("包含匹配")
        private String rContent;
        @ApiModelProperty("完全匹配")
        private String content;

        @Override
        public void validThrow() {
            ValidUtils.isFalse(StringUtils.isAllBlank(rContent, content), "请设置消息匹配的文本");
        }
    }

    @Data
    @Accessors(chain = true)
    @ApiModel("匹配消息类型::事件")
    public static class Event implements Valid {
        @ApiModelProperty("事件类型")
        private String event;
        @ApiModelProperty("事件类型Key")
        private String eventKey;

        @Override
        public void validThrow() {
            ValidUtils.isFalse(StringUtils.isAllBlank(event, eventKey), "请设置消息匹配的事件");
        }
    }
}
