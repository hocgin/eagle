package in.hocg.eagle.modules.ums.pojo.qo.authority;

import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.PatternConstant;
import in.hocg.eagle.basic.constant.datadict.Platform;
import in.hocg.eagle.basic.pojo.ro.BaseRo;
import in.hocg.eagle.basic.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorityInsertQo extends BaseRo {

    @NotBlank(message = "权限名称")
    @ApiModelProperty("权限名称")
    private String title;

    @NotNull(message = "权限类型")
    @ApiModelProperty("权限类型")
    private Integer type;

    @NotBlank(message = "权限授权码")
    @ApiModelProperty("权限授权码")
    @Pattern(regexp = PatternConstant.ONLY_NUMBER_OR_WORD, message = "仅支持数字和字母组合")
    private String authorityCode;

    @NotNull(message = "启用状态")
    @ApiModelProperty("启用状态")
    @EnumRange(enumClass = Enabled.class)
    private Integer enabled;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("父级")
    private Long parentId;

    @NotNull(message = "平台不能为空")
    @EnumRange(enumClass = Platform.class)
    @ApiModelProperty("平台")
    private Integer platform;

}
