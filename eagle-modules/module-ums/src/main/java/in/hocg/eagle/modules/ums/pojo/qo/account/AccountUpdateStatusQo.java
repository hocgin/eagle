package in.hocg.eagle.modules.ums.pojo.qo.account;

import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.constant.datadict.Expired;
import in.hocg.web.constant.datadict.Locked;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.web.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountUpdateStatusQo extends IdQo {
    @ApiModelProperty("启用状态")
    @EnumRange(enumClass = Enabled.class)
    private Integer enabled;
    @ApiModelProperty("锁定状态")
    @EnumRange(enumClass = Locked.class)
    private Integer locked;
    @ApiModelProperty("过期状态")
    @EnumRange(enumClass = Expired.class)
    private Integer expired;
}
