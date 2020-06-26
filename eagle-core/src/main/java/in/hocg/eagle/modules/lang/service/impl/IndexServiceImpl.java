package in.hocg.eagle.modules.lang.service.impl;

import in.hocg.eagle.basic.ext.security.authentication.token.TokenUtility;
import in.hocg.eagle.manager.sms.SmsManager;
import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.com.service.ShortUrlService;
import in.hocg.eagle.modules.lang.pojo.SendSmsCodeRo;
import in.hocg.eagle.modules.lang.service.IndexService;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountSignUpQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.ChangePasswordUseSmsCodeQo;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.eagle.utils.LangUtils;
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
    public void sendSmsCode(SendSmsCodeRo qo) {
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
        final String token = accountService.changePasswordUseSmsCode(qo);
        return TokenUtility.encode(token);
    }
}
