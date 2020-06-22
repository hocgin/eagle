package in.hocg.eagle.basic.security.social;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by hocgin on 2020/5/10.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class SocialUserInfo {
    protected Map<String, Object> attributes;

    public SocialUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract Serializable getId();
}
