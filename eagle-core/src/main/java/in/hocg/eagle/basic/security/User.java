package in.hocg.eagle.basic.security;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class User extends org.springframework.security.core.userdetails.User {
    
    
    public User(Serializable id,
                String username,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    
    
}
