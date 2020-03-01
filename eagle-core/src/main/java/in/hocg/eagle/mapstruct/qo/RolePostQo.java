package in.hocg.eagle.mapstruct.qo;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.constant.PatternConstant;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.datadict.Platform;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.valid.RangeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RolePostQo extends BaseQo {
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty("角色名称")
    private String title;
    @Pattern(regexp = PatternConstant.ONLY_NUMBER_OR_WORD, message = "仅支持数字和字母组合")
    @NotBlank(message = "角色授权码不能为空")
    @ApiModelProperty("角色授权码")
    private String roleCode;
    @ApiModelProperty("角色描述")
    private String remark;
    @NotNull(message = "启用状态不能为空")
    @RangeEnum(enumClass = Enabled.class)
    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    private Integer enabled;
    @NotNull(message = "平台不能为空")
    @RangeEnum(enumClass = Platform.class)
    @ApiModelProperty("平台")
    private Integer platform;
    @ApiModelProperty("权限列表")
    private List<Long> authorities = Lists.newArrayList();
}
