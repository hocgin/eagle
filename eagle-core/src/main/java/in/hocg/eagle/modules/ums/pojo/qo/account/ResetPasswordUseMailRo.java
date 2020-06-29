package in.hocg.eagle.modules.ums.pojo.qo.account;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/6/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResetPasswordUseMailRo extends BaseRo {
    @NotBlank(message = "邮箱地址不能为空")
    @ApiModelProperty("邮箱地址")
    private String email;
}
