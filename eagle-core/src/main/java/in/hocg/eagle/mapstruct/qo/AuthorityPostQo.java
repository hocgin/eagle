package in.hocg.eagle.mapstruct.qo;

import in.hocg.eagle.basic.constant.Enabled;
import in.hocg.eagle.basic.constant.PatternConstant;
import in.hocg.eagle.basic.qo.BaseQo;
import in.hocg.eagle.basic.valid.RangeEnum;
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
public class AuthorityPostQo extends BaseQo {
    
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
    
    @NotBlank(message = "请求方式")
    @ApiModelProperty("请求方式")
    private String method;
    
    @NotBlank(message = "请求URL")
    @ApiModelProperty("请求URL")
    private String uri;
    
    @NotNull(message = "启用状态")
    @ApiModelProperty("启用状态")
    @RangeEnum(enumClass = Enabled.class)
    private Integer enabled;
    
    @NotNull(message = "排序")
    @ApiModelProperty("排序")
    private Integer sort;
    
    @ApiModelProperty("父级")
    private Long parentId;
    
}
