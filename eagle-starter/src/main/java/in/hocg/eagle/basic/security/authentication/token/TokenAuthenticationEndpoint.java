package in.hocg.eagle.basic.security.authentication.token;

import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.constant.datadict.Expired;
import in.hocg.web.constant.datadict.Locked;
import in.hocg.web.exception.ServiceException;
import in.hocg.web.result.Result;
import in.hocg.web.manager.SmsManager;
import in.hocg.eagle.modules.ums.entity.Account;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.web.security.TokenUtility;
import in.hocg.web.utils.LangUtils;
import in.hocg.web.utils.ValidUtils;
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

import java.util.Optional;

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
    public static final String LOGIN_USE_SMS_CODE_ACCOUNT_TOKEN_URI = "/api/account/authenticate:sms-code";

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final SmsManager smsManager;
    private final AccountService accountService;

    @UseLogger("获取TOKEN")
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

    @UseLogger("获取TOKEN")
    @PostMapping(TokenAuthenticationEndpoint.LOGIN_USE_SMS_CODE_ACCOUNT_TOKEN_URI)
    public Result<String> authenticateUseSmsCode(@Validated @RequestBody SmsCodeTokenQo qo) {
        final String phone = qo.getPhone();
        final String smsCode = qo.getSmsCode();
        ValidUtils.isTrue(smsManager.validSmsCode(phone, smsCode), "手机号码或验证码错误");
        final Optional<Account> accountOpt = accountService.selectOneByPhone(phone);
        if (!accountOpt.isPresent()) {
            throw ServiceException.wrap("账号不存在");
        }
        final Account account = accountOpt.get();
        ValidUtils.isTrue(LangUtils.equals(account.getEnabled(), Enabled.On.getCode()), "账号被禁用");
        ValidUtils.isTrue(LangUtils.equals(account.getExpired(), Expired.On.getCode()), "账号已过期");
        ValidUtils.isTrue(LangUtils.equals(account.getLocked(), Locked.On.getCode()), "账号被锁定");

        return Result.success(TokenUtility.encode(account.getUsername()));
    }
}
