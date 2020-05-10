package in.hocg.eagle.modules.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
    /**
     * 订单ID
     */
    @TableField("order_id")
    private Long orderId;
    /**
     * 商品ID
     */
    @TableField("product_id")
    private Long productId;
    /**
     * 商品主图
     */
    @TableField("product_pic")
    private String productPic;
    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;
    /**
     * 销售价格
     */
    @TableField("product_price")
    private BigDecimal productPrice;
    /**
     * 购买数量
     */
    @TableField("product_quantity")
    private Integer productQuantity;
    /**
     * 商品SKU ID
     */
    @TableField("product_sku_id")
    private Long productSkuId;
    /**
     * 商品SKU条码
     */
    @TableField("product_sku_code")
    private String productSkuCode;
    /**
     * 优惠券优惠分解金额
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;
    /**
     * [计算型]该商品经过优惠后的分解金额(总价)
     */
    @TableField("real_amount")
    private BigDecimal realAmount;
    /**
     * 商品规格:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    @TableField("product_spec_data")
    private String productSpecData;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
