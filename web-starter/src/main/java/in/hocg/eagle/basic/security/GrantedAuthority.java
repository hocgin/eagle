package in.hocg.eagle.basic.security;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {
    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
