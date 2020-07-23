package in.hocg.eagle.basic.constant.config;

import cn.hutool.script.ScriptUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Created by hocgin on 2020/7/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@RequiredArgsConstructor
public enum ConfigEnum {
    OrderExpireTime("订单过期时间(单位:秒)", "3600", "", Integer.class),
    @Deprecated
    Test("XX", "3600", "", Integer.class);
    private final String title;
    private final String defaultValue;
    private final String remark;
    private final Class<?> clazz;

    public <T> T eval(String text) {
        if (Objects.isNull(text)) {
            return null;
        }
        final Class<?> clazz = getClazz();
        if (String.class.equals(clazz)) {
            return (T) text;
        }
        return (T) ScriptUtil.eval(text);
    }
}
