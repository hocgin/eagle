package in.hocg.eagle.modules.lang.service.impl;

import in.hocg.web.manager.SmsManager;
import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.com.service.ShortUrlService;
import in.hocg.eagle.modules.lang.pojo.SendSmsCode;
import in.hocg.eagle.modules.lang.service.IndexService;
import in.hocg.eagle.modules.ums.entity.Account;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountSignUpQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.ChangePasswordUseSmsCodeQo;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.exception.ServiceException;
import in.hocg.web.security.TokenUtility;
import in.hocg.web.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by hocgin on 2020/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class IndexServiceImpl implements IndexService {
    private final ShortUrlService shortUrlService;
    private final AccountService accountService;
    private final SmsManager smsManager;

    @Override
    public String signUp(AccountSignUpQo qo) {
        return accountService.signUp(qo);
    }

    @Override
    public void sendSmsCode(SendSmsCode qo) {
        final String phone = qo.getPhone();
        String smsCode = String.valueOf(LangUtils.getIntRandomCode(4));
        smsManager.sendSmsCode(phone, smsCode);
    }

    @Override
    public Optional<ShortUrl> selectOneByCode(String code) {
        return shortUrlService.selectOneByCode(code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String changePasswordUsePhone(ChangePasswordUseSmsCodeQo qo) {
        final String phone = qo.getPhone();
        final String smsCode = qo.getSmsCode();
        final String password = qo.getPassword();
        if (!smsManager.validSmsCode(phone, smsCode)) {
            throw ServiceException.wrap("验证码错误");
        }
        final Optional<Account> accountOpt = accountService.selectOneByPhone(phone);
        if (!accountOpt.isPresent()) {
            throw ServiceException.wrap("找不到账号");
        }
        final Account account = accountOpt.get();
        if (!LangUtils.equals(Enabled.On.getCode(), account.getEnabled())) {
            throw ServiceException.wrap("账号被禁用了");
        }

        accountService.changePassword(account.getId(), password);
        return TokenUtility.encode(account.getUsername());
    }
}
