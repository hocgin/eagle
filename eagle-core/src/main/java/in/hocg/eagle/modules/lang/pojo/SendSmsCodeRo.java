package in.hocg.eagle.modules.lang.pojo;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SendSmsCodeRo extends BaseRo {
    @NotBlank(message = "手机号码错误")
    private String phone;
}
