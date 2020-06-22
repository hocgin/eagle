package in.hocg.eagle.modules.mkt.pojo.qo;

import in.hocg.web.pojo.qo.PageQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class CouponAccountPagingQo extends PageQo {
    @ApiModelProperty("拥有人")
    private Long accountId;
    @ApiModelProperty("优惠券ID")
    private Long couponId;
}
