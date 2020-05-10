package in.hocg.eagle.modules.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * [订单模块] 购物车表
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_cart_item")
public class CartItem extends AbstractEntity<CartItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("账号ID")
    @TableField("account_id")
    private Long accountId;
    @ApiModelProperty("商品ID")
    @TableField("product_id")
    private Long productId;
    @ApiModelProperty("加入时，商品价格")
    @TableField("add_product_price")
    private BigDecimal addProductPrice;
    @ApiModelProperty("加入时，商品标题")
    @TableField("add_product_title")
    private String addProductTitle;
    @ApiModelProperty("加入时，商品图片")
    @TableField("add_product_image_url")
    private String addProductImageUrl;
    @ApiModelProperty("SKU ID")
    @TableField("sku_id")
    private Long skuId;
    @ApiModelProperty("规格属性")
    @TableField("sku_spec_data")
    private String skuSpecData;
    @ApiModelProperty("加入的数量")
    @TableField("quantity")
    private Integer quantity;
    @TableField("creator")
    private Long creator;
    @TableField("created_at")
    private LocalDateTime createdAt;
    @TableField("last_updater")
    private Long lastUpdater;
    @TableField("last_updated_at")
    private LocalDateTime lastUpdatedAt;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
