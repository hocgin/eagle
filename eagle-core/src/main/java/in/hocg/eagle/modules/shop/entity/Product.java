package in.hocg.eagle.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import in.hocg.eagle.basic.AbstractEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@TableName("shop_product")
public class Product extends AbstractEntity<Product> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 产品分类(t_product_classify)ID
     */
    @TableField("classify_id")
    private Long classifyId;
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
     * 上架状态: [下架, 上架]
     */
    @TableField("status")
    private Integer status;
    @TableField("creator")
    private Long creator;
    @TableField("created_at")
    private LocalDateTime createdAt;
    @TableField("last_updater")
    private Long lastUpdater;
    @TableField("last_updated_at")
    private LocalDateTime lastUpdatedAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
