package in.hocg.eagle.modules.ums.pojo.qo.account;

import in.hocg.eagle.basic.pojo.ro.CompleteRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/3/31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountCompleteQo extends CompleteRo {
    @ApiModelProperty("昵称/用户名/手机号/邮箱")
    private String keyword;
}
