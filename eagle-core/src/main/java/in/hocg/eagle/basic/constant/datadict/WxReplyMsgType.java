package in.hocg.eagle.basic.constant.datadict;

import in.hocg.eagle.basic.constant.JSONasClass;
import in.hocg.eagle.basic.constant.Valid;
import in.hocg.eagle.utils.ValidUtils;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by hocgin on 2020/5/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("回复消息类型")
@RequiredArgsConstructor
public enum WxReplyMsgType implements JSONasClass, DataDictEnum {
    Text(0, "文本", Text.class);
    private final Integer code;
    private final String name;
    private final Class<? extends Valid> clazz;
    public static final String KEY = "wxReplyMsgType";

    public <T extends Valid> T asObject(String json) {
        return (T) this.asClass(json, this.getClazz());
    }

    @Data
    @Accessors(chain = true)
    @ApiModel("回复消息类型::文本")
    public static class Text implements Valid {
        private String content;

        @Override
        public void validThrow() {
            ValidUtils.isFalse(StringUtils.isAllBlank(content), "回复内容不能为空");
        }
    }
}
