package in.hocg.eagle.modules.lang.service;

import in.hocg.eagle.modules.lang.pojo.ro.SendSmsCodeRo;

/**
 * Created by hocgin on 2020/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface IndexService {

    void sendSmsCode(SendSmsCodeRo qo);

    String selectOneByCode(String code);
}
