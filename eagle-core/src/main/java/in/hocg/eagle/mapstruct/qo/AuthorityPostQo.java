package in.hocg.eagle.mapstruct.qo;

import in.hocg.eagle.basic.qo.BaseQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorityPostQo extends BaseQo {
    
    @NotBlank
    @ApiModelProperty("权限名称")
    private String title;
    
    @NotNull
    @ApiModelProperty("权限类型")
    private Integer type;
    
    @NotBlank
    @ApiModelProperty("权限授权码")
    private String authorityCode;
    
    @NotBlank
    @ApiModelProperty("请求方式")
    private String method;
    
    @NotBlank
    @ApiModelProperty("请求URL")
    private String uri;
    
    @NotNull
    @ApiModelProperty("启用状态")
    private Integer enabled;
    
    @NotNull
    @ApiModelProperty("排序")
    private Integer sort;
    
}
