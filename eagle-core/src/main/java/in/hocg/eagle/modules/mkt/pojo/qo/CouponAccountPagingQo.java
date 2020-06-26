package in.hocg.eagle.modules.mkt.pojo.qo;

import in.hocg.eagle.basic.pojo.ro.PageRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class CouponAccountPagingQo extends PageRo {
    @ApiModelProperty("拥有人")
    private Long accountId;
    @ApiModelProperty("优惠券ID")
    private Long couponId;
}
