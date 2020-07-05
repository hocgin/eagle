package in.hocg.eagle.modules.oms.pojo.vo.order;

import in.hocg.eagle.modules.oms.helper.order.discount.DiscountComplex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/7/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@ApiModel("可用优惠信息")
public class AvailableDiscountVo {
    @ApiModelProperty("可用优惠券列表")
    private List<DiscountComplex> availableCoupon = Collections.emptyList();
    @ApiModelProperty("不可用优惠券列表")
    private List<DiscountComplex> unavailableCoupon = Collections.emptyList();
}
