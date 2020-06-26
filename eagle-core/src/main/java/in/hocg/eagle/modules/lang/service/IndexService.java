package in.hocg.eagle.modules.lang.service;

import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.lang.pojo.SendSmsCodeRo;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountSignUpQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.ChangePasswordUseSmsCodeQo;

import java.util.Optional;

/**
 * Created by hocgin on 2020/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface IndexService {

    String signUp(AccountSignUpQo qo);

    void sendSmsCode(SendSmsCodeRo qo);

    Optional<ShortUrl> selectOneByCode(String code);

    String changePasswordUsePhone(ChangePasswordUseSmsCodeQo qo);
}
