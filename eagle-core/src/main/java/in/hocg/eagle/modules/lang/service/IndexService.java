package in.hocg.eagle.modules.lang.service;

import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.lang.pojo.SendSmsCode;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountSignUpQo;

import java.util.Optional;

/**
 * Created by hocgin on 2020/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface IndexService {

    void signUp(AccountSignUpQo qo);

    void sendSmsCode(SendSmsCode qo);

    Optional<ShortUrl> selectOneByCode(String code);
}
