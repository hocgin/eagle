package in.hocg.eagle.mapstruct.qo;

import in.hocg.eagle.basic.qo.IdQo;
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
public class AuthorityPutQo extends IdQo {
    
    @NotBlank(message = "权限名称")
    @ApiModelProperty("权限名称")
    private String title;
    
    @NotNull(message = "权限类型")
    @ApiModelProperty("权限类型")
    private Integer type;
    
    @NotBlank(message = "权限授权码")
    @ApiModelProperty("权限授权码")
    private String authorityCode;
    
    @NotBlank(message = "请求方式")
    @ApiModelProperty("请求方式")
    private String method;
    
    @NotBlank(message = "请求URL")
    @ApiModelProperty("请求URL")
    private String uri;
    
    @NotNull(message = "启用状态")
    @ApiModelProperty("启用状态")
    private Integer enabled;
    
    @NotNull(message = "排序")
    @ApiModelProperty("排序")
    private Integer sort;
    
    @ApiModelProperty("父级")
    private Integer parentId;
}
