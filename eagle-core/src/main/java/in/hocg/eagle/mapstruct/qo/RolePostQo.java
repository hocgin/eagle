package in.hocg.eagle.mapstruct.qo;

import in.hocg.eagle.basic.qo.BaseQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "角色授权码不能为空")
    @ApiModelProperty("角色授权码")
    private String roleCode;
    @ApiModelProperty("角色描述")
    private String remark;
    @NotBlank(message = "启用状态不能为空")
    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    private Integer enabled;
}
