package in.hocg.eagle.modules.ums.pojo.qo.role;

import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.constant.PatternConstant;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.web.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleUpdateQo extends IdQo {
    @ApiModelProperty("角色名称")
    private String title;
    @Pattern(regexp = PatternConstant.ONLY_NUMBER_OR_WORD, message = "仅支持数字和字母组合")
    @ApiModelProperty("角色授权码")
    private String roleCode;
    @ApiModelProperty("角色描述")
    private String remark;
    @EnumRange(enumClass = Enabled.class)
    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    private Integer enabled;
}
