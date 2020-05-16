package in.hocg.eagle.basic.constant.datadict.wx.reply;

import in.hocg.eagle.basic.constant.datadict.IntJSONEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.builder.outxml.BaseBuilder;

import java.util.Optional;

/**
 * Created by hocgin on 2020/5/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("回复消息类型")
@RequiredArgsConstructor
public enum WxReplyMsgType implements IntJSONEnum {
    Text(0, "文本", Text.class);
    private final Integer code;
    private final String name;
    private final Class<?> clazz;
    public static final String KEY = "wxReplyMsgType";

    public <T> T asObject(String json) {
        return (T) this.asClass(json, this.getClazz());
    }

    public Optional<BaseBuilder<?, ? extends WxMpXmlOutMessage>> asWxMpMessageBuilder(String replyContent) {
        BaseBuilder<?, ? extends WxMpXmlOutMessage> result = null;
        switch (this) {
            case Text: {
                final Text replyObject = asObject(replyContent);
                result = WxMpXmlOutMessage.TEXT()
                    .content(replyObject.getContent());
                break;
            }
            default:
        }
        return Optional.ofNullable(result);
    }

    @Data
    @Accessors(chain = true)
    @ApiModel("回复消息类型::文本")
    public static class Text {
        private String content;
    }
}
