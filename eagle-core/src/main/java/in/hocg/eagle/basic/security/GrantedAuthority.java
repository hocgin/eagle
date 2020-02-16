package in.hocg.eagle.basic.security;

import lombok.Data;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {
    
    private String method;
    private String uri;
    private String authorityCode;
    
    @Override
    public String getAuthority() {
        return this.authorityCode;
    }
}
