package in.hocg.eagle.modules.shop.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import in.hocg.eagle.basic.AbstractEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@TableName("shop_sku")
public class Sku extends AbstractEntity<Sku> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * SKU 编码
     */
    @TableField("sku")
    private String sku;
    /**
     * 价格(如: 12.00)
     */
    @TableField("price")
    private BigDecimal price;
    /**
     * 库存, 默认为0
     */
    @TableField("inventory")
    private Integer inventory;
    /**
     * 该特色商品图片
     */
    @TableField("image_url")
    private String imageUrl;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
