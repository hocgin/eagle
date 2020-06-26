package in.hocg.eagle.modules.pms.pojo.qo;

import in.hocg.eagle.basic.pojo.ro.PageRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class ProductPagingQo extends PageRo {
    @ApiModelProperty("发布状态")
    private Integer publishStatus;
}
