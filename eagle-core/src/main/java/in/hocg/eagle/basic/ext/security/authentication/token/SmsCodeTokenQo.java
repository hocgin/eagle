package in.hocg.eagle.basic.ext.security.authentication.token;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * Created by hocgin on 2020/5/27.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SmsCodeTokenQo extends BaseRo {
    @NotEmpty(message = "手机号码不能为空")
    private String phone;
    @NotEmpty(message = "验证码不能为空")
    private String smsCode;
}
