package in.hocg.eagle.modules.oms.pojo.vo.order;

import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.DiscountType;
import in.hocg.eagle.basic.pojo.KeyValue;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Collections;
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
    private List<OrderItem> items = Collections.emptyList();
    @ApiModelProperty("订单总价")
    private BigDecimal totalAmount;
    @ApiModelProperty("应付金额")
    private BigDecimal payAmount;
    @ApiModelProperty("[计算型]优惠总金额")
    private BigDecimal discountTotalAmount;
    @ApiModelProperty("优惠券抵扣金额")
    private BigDecimal couponDiscountAmount;
    @ApiModelProperty("默认收货地址")
    private AccountAddressComplexVo defaultAddress;

    @ApiModelProperty("优惠明细")
    private List<DiscountInfo> discounts = Collections.emptyList();


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
        @ApiModelProperty("规格")
        private List<KeyValue> spec = Collections.emptyList();
        @ApiModelProperty("购买数量")
        private Integer quantity;
        @ApiModelProperty("商品单价")
        private BigDecimal price;
        @ApiModelProperty("优惠分解金额(不含后台调价)")
        private BigDecimal discountAmount;
        @ApiModelProperty("原总金额")
        private BigDecimal totalAmount;
        @ApiModelProperty("优惠后的金额=原总金额-优惠券分解优惠的金额-管理员调整优惠的金额")
        private BigDecimal realAmount;
    }

    @Data
    @Accessors(chain = true)
    public static class DiscountInfo {
        @ApiModelProperty("优惠信息ID")
        private Long id;
        @ApiModelProperty("优惠信息类型")
        private Integer type;
        @Named(idFor = "type", type = NamedType.DataDict, args = {DiscountType.KEY})
        private String typeName;
        @ApiModelProperty("优惠金额")
        private BigDecimal discountAmount;
        @ApiModelProperty("优惠名称")
        private String title;
    }

}
