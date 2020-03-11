package in.hocg.eagle.mapstruct.qo.authority;

import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.PatternConstant;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.valid.RangeEnum;
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
public class AuthorityUpdateQo extends IdQo {

    @ApiModelProperty("权限名称")
    private String title;

    @ApiModelProperty("权限类型")
    private Integer type;

    @ApiModelProperty("权限授权码")
    @Pattern(regexp = PatternConstant.ONLY_NUMBER_OR_WORD, message = "仅支持数字和字母组合")
    private String authorityCode;

    @ApiModelProperty("启用状态")
    @RangeEnum(enumClass = Enabled.class)
    private Integer enabled;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("父级")
    private Integer parentId;
}
