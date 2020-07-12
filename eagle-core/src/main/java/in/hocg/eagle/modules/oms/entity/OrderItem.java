package in.hocg.eagle.modules.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * [订单模块] 订单商品表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_order_item")
public class OrderItem extends AbstractEntity<OrderItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("order_id")
    @ApiModelProperty("订单ID")
    private Long orderId;
    @TableField("product_id")
    @ApiModelProperty("商品ID")
    private Long productId;
    @TableField("product_pic")
    @ApiModelProperty("商品主图")
    private String productPic;
    @TableField("product_name")
    @ApiModelProperty("商品名称")
    private String productName;
    @TableField("product_price")
    @ApiModelProperty("销售价格")
    private BigDecimal productPrice;
    @TableField("product_quantity")
    @ApiModelProperty("购买数量")
    private Integer productQuantity;
    @TableField("product_sku_id")
    @ApiModelProperty("商品SKU ID")
    private Long productSkuId;
    @TableField("product_sku_code")
    @ApiModelProperty("商品SKU条码")
    private String productSkuCode;
    @TableField("product_spec_data")
    @ApiModelProperty("商品规格:[{\"key\":\"颜色\",\"value\":\"颜色\"},{\"key\":\"容量\",\"value\":\"4G\"}]")
    private String productSpecData;

    @TableField("total_amount")
    @ApiModelProperty("[计算型]原总价=销售价格x购买数量")
    private BigDecimal totalAmount;
    @ApiModelProperty("后台调整优惠")
    @TableField("adjustment_discount_amount")
    private BigDecimal adjustmentDiscountAmount;
    @TableField("discount_amount")
    @ApiModelProperty("优惠分解金额(不含后台调整优惠)")
    private BigDecimal discountAmount;
    @TableField("real_amount")
    @ApiModelProperty("[计算型]该商品经过优惠后的分解金额(实际支付金额)=原总价-后台调整优惠-优惠分解金额")
    private BigDecimal realAmount;

}
