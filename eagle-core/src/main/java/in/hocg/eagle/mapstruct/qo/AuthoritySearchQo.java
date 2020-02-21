package in.hocg.eagle.mapstruct.qo;

import in.hocg.eagle.basic.constant.Platform;
import in.hocg.eagle.basic.qo.PageQo;
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
