package in.hocg.eagle.modules.shop.pojo.qo;

import in.hocg.eagle.basic.pojo.qo.PageQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class ProductPagingQo extends PageQo {
    @ApiModelProperty("发布状态")
    private Integer publishStatus;
}
