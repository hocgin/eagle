package in.hocg.eagle.mapstruct.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class RoleComplexDto implements Serializable {
    @ApiModelProperty("ID")
    private Integer id;
    @ApiModelProperty("角色名称")
    private String title;
    @ApiModelProperty("角色授权码")
    private String roleCode;
    @ApiModelProperty("角色描述")
    private String remark;
    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    private Integer enabled;
    @ApiModelProperty("权限列表")
    private List<AuthorityDto> authorities = Lists.newArrayList();
    
    @Data
    public static class AuthorityDto implements Serializable {
        @ApiModelProperty("ID")
        private Integer id;
        @ApiModelProperty("权限名称")
        private String title;
        @ApiModelProperty("权限类型")
        private Integer type;
        @ApiModelProperty("权限授权码")
        private String authorityCode;
    }
}
