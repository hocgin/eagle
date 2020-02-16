package in.hocg.eagle.mapstruct.qo;

import in.hocg.eagle.basic.constant.Enabled;
import in.hocg.eagle.basic.qo.IdQo;
import in.hocg.eagle.basic.valid.RangeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorityPutQo extends IdQo {
    
    @ApiModelProperty("权限名称")
    private String title;
    
    @ApiModelProperty("权限类型")
    private Integer type;
    
    @ApiModelProperty("权限授权码")
    private String authorityCode;
    
    @ApiModelProperty("请求方式")
    private String method;
    
    @ApiModelProperty("请求URL")
    private String uri;
    
    @ApiModelProperty("启用状态")
    @RangeEnum(enumClass = Enabled.class)
    private Integer enabled;
    
    @ApiModelProperty("排序")
    private Integer sort;
    
    @ApiModelProperty("父级")
    private Integer parentId;
}
