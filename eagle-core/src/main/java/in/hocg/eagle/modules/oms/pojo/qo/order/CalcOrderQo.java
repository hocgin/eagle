package in.hocg.eagle.modules.oms.pojo.qo.order;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CalcOrderQo extends BaseQo {
    @NotNull
    @Size(min = 1)
    @ApiModelProperty("订单项")
    private List<Item> items = Lists.newArrayList();
    @ApiModelProperty("优惠券ID")
    private Long couponId;

    @Data
    public static class Item {
        @NotNull
        @ApiModelProperty("购买商品的SKU")
        private Long skuId;
        @NotNull
        @Min(value = 1)
        @ApiModelProperty("购买数量")
        private Integer quantity;
    }
}
