package in.hocg.eagle.modules.shop.entity;

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
import java.time.LocalDateTime;

/**
 * <p>
 * [Shop模块] 商品表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_product")
public class Product extends AbstractEntity<Product> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商品品类(t_product_classify)ID
     */
    @TableField("product_category_id")
    private Long productCategoryId;
    /**
     * video url
     */
    @TableField("video_url")
    private String videoUrl;
    /**
     * 采购地(中国,福建)
     */
    @TableField("procurement")
    private String procurement;
    /**
     * 产品名
     */
    @TableField("title")
    private String title;
    /**
     * 产品属性: {}
     */
    @TableField("attrs")
    private String attrs;
    /**
     * 上架状态: [0:下架, 1:上架]
     */
    @TableField("publish_status")
    private Integer publishStatus;
    /**
     * 删除状态: [0:未删除, 1:删除]
     */
    @TableField("delete_status")
    private Integer deleteStatus;
    /**
     * 单位
     */
    @TableField("unit")
    private String unit;
    /**
     * 商品重量(克)
     */
    @TableField("weight")
    private BigDecimal weight;
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
