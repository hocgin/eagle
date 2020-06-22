package in.hocg.eagle.modules.lang.service.impl;

import in.hocg.eagle.modules.com.api.ShortUrlAPI;
import in.hocg.eagle.modules.lang.pojo.ro.SendSmsCodeRo;
import in.hocg.eagle.modules.lang.service.IndexService;
import in.hocg.web.manager.SmsManager;
import in.hocg.web.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
    private final ShortUrlAPI shortUrlAPI;
    private final SmsManager smsManager;

    @Override
    public void sendSmsCode(SendSmsCodeRo qo) {
        final String phone = qo.getPhone();
        String smsCode = String.valueOf(LangUtils.getIntRandomCode(4));
        smsManager.sendSmsCode(phone, smsCode);
    }

    @Override
    public String selectOneByCode(String code) {
        return shortUrlAPI.selectOneByCode(code);
    }

}
