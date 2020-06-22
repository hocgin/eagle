package in.hocg.eagle.modules.ums.pojo.qo.authority;

import in.hocg.web.constant.datadict.Platform;
import in.hocg.web.pojo.qo.PageQo;
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
public class AuthoritySearchQo extends PageQo {
    @ApiModelProperty("父ID")
    private Long parentId;
    @ApiModelProperty("平台")
    private Integer platform = Platform.Eagle.getCode();
}
