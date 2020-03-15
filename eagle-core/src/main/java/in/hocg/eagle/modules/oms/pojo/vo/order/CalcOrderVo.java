package in.hocg.eagle.modules.oms.pojo.vo.order;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class CalcOrderVo {
    @ApiModelProperty("商品项")
    private List<OrderItem> orderItems = Lists.newArrayList();
    @ApiModelProperty("订单总价")
    private BigDecimal totalAmount;
    @ApiModelProperty("应付金额")
    private BigDecimal payAmount;
    @ApiModelProperty("优惠券")
    private Coupon coupon;

    @Data
    @Accessors(chain = true)
    public static class OrderItem {
        @ApiModelProperty("SKU ID")
        private Long skuId;
        @ApiModelProperty("PRODUCT ID")
        private Long productId;
        @ApiModelProperty("商品标题")
        private String title;
        @ApiModelProperty("商品图片")
        private String imageUrl;
        @ApiModelProperty("SKU编码")
        private String skuCode;
        @ApiModelProperty("规格")
        private String specData;
        @ApiModelProperty("购买数量")
        private Integer quantity;
        @ApiModelProperty("商品单价")
        private BigDecimal price;
        @ApiModelProperty("优惠券分解优惠的金额")
        private BigDecimal couponAmount;
        @ApiModelProperty("优惠后的金额")
        private BigDecimal realAmount;
    }

    @Data
    @Accessors(chain = true)
    public static class Coupon {
        @ApiModelProperty("优惠券 ID")
        private Long id;
        @ApiModelProperty("优惠金额")
        private BigDecimal couponAmount;
    }
}
