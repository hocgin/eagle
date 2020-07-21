package in.hocg.eagle.manager.redis;

import lombok.experimental.UtilityClass;

/**
 * Created by hocgin on 2020/5/27.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class RedisConstants {
    public enum Prefix {
        SMS,
        TOKEN
    }

    public String getTokenKey(String username) {
        return prefix(Prefix.TOKEN, username);
    }

    public String getSmsKey(String phone) {
        return prefix(Prefix.SMS, phone);
    }

    private String prefix(Prefix prefix, String suffix) {
        return String.format("%s::%s", prefix.name(), suffix);
    }
}
