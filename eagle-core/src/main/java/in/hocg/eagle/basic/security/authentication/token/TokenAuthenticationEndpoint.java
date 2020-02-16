package in.hocg.eagle.basic.security.authentication.token;

import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hocgin on 2020/1/9.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class TokenAuthenticationEndpoint {
    public static final String ACCOUNT_TOKEN_URI = "/api/account/authenticate";
    
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    
    @PostMapping(TokenAuthenticationEndpoint.ACCOUNT_TOKEN_URI)
    public Result<String> authenticate(@Validated @RequestBody TokenQo qo) {
        final String username = qo.getUsername();
        final String password = qo.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            throw ServiceException.wrap("用户名或密码错误");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = TokenUtility.encode(userDetails.getUsername());
        return Result.success(token);
    }
}
