package in.hocg.eagle.modules.ums.pojo.qo.account;

import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.datadict.Expired;
import in.hocg.eagle.basic.constant.datadict.Locked;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.basic.valid.EnumRange;
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
public class AccountUpdateStatusQo extends IdRo {
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
