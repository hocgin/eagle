package in.hocg.eagle.modules.ums.pojo.qo.authority;

import in.hocg.eagle.basic.constant.datadict.Platform;
import in.hocg.eagle.basic.pojo.ro.PageRo;
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
public class AuthoritySearchQo extends PageRo {
    @ApiModelProperty("父ID")
    private Long parentId;
    @ApiModelProperty("平台")
    private Integer platform = Platform.Eagle.getCode();
}
