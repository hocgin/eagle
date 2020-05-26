package in.hocg.eagle.modules.lang.service.impl;

import in.hocg.eagle.manager.RedisManager;
import in.hocg.eagle.manager.SmsManager;
import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.com.service.ShortUrlService;
import in.hocg.eagle.modules.lang.pojo.SendSmsCode;
import in.hocg.eagle.modules.lang.service.IndexService;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountSignUpQo;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.eagle.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
    private final RedisManager redisManager;
    private final SmsManager smsManager;

    @Override
    public void signUp(AccountSignUpQo qo) {
        accountService.signUp(qo);
    }

    @Override
    public void sendSmsCode(SendSmsCode qo) {
        final String phone = qo.getPhone();
        String smsCode = String.valueOf(LangUtils.getIntRandomCode(4));
        smsManager.sendSmsCode(phone, smsCode);
        redisManager.setSmsCode(phone, smsCode);
    }

    @Override
    public Optional<ShortUrl> selectOneByCode(String code) {
        return shortUrlService.selectOneByCode(code);
    }
}
