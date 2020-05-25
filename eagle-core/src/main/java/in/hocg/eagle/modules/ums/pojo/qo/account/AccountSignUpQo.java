package in.hocg.eagle.modules.ums.pojo.qo.account;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountSignUpQo extends BaseQo {
    private String nickname;
    private String username;
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    @NotBlank(message = "短信不能为空")
    private String smsCode;
}
