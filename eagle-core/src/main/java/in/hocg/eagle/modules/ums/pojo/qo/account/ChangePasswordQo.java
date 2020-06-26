package in.hocg.eagle.modules.ums.pojo.qo.account;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/4/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChangePasswordQo extends BaseRo {
    @NotBlank(message = "信息缺失")
    @NotNull(message = "信息缺失")
    @ApiModelProperty("验证Token")
    private String token;
    @NotNull(message = "请输入邮箱号")
    @Email(message = "请输入邮箱号")
    @ApiModelProperty("邮箱")
    private String mail;
    @NotNull(message = "请输入新密码")
    @Length(min = 6, max = 32, message = "密码长度不符合要求(6~32)")
    @ApiModelProperty("新密码")
    private String newPassword;
}
