package in.hocg.eagle.basic.ext.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class User extends org.springframework.security.core.userdetails.User {
    @Getter
    private final Long id;

    public User(Long id,
                String username,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }


}
