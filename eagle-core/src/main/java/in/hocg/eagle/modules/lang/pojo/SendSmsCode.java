package in.hocg.eagle.modules.lang.pojo;

import in.hocg.web.pojo.qo.BaseQo;
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
public class SendSmsCode extends BaseQo {
    @NotBlank(message = "手机号码错误")
    private String phone;
}
