package in.hocg.eagle.modules.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.web.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * [Shop模块] 商品SKU表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_sku")
public class Sku extends AbstractEntity<Sku> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("product_id")
    private Long productId;
    /**
     * SKU 编码
     */
    @TableField("sku_code")
    private String skuCode;
    /**
     * 商品价格(如: 12.00)
     */
    @TableField("price")
    private BigDecimal price;
    /**
     * 库存, 默认为0
     */
    @TableField("stock")
    private Integer stock;
    /**
     * 销量, 默认为0
     */
    @TableField("sale")
    private Integer sale;
    /**
     * 规格属性(JSONArray, 如: [{"key":"颜色","value":"银色"}])
     */
    @TableField("spec_data")
    private String specData;
    /**
     * 该特色商品图片
     */
    @TableField("image_url")
    private String imageUrl;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
