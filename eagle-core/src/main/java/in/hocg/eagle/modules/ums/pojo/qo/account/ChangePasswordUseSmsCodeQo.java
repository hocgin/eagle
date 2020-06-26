package in.hocg.eagle.modules.ums.pojo.qo.account;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/5/27.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChangePasswordUseSmsCodeQo extends BaseRo {
    @NotBlank(message = "手机号码不能为空")
    @ApiModelProperty("手机号码")
    private String phone;
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String smsCode;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    @ApiModelProperty("确认密码")
    private String confirmPassword;
}
