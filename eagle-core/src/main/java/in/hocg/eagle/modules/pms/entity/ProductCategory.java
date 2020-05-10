package in.hocg.eagle.modules.pms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.mybatis.tree.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * [Shop模块] 商品品类表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_product_category")
public class ProductCategory extends TreeEntity<ProductCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品品类名称
     */
    @TableField("title")
    private String title;
    /**
     * 商品品类描述
     */
    @TableField("remark")
    private String remark;
    /**
     * 商品品类图片
     */
    @TableField("image_url")
    private String imageUrl;
    /**
     * 商品品类关键词
     */
    @TableField("keywords")
    private String keywords;
    /**
     * 排序, 从大到小降序
     */
    @TableField("sort")
    private Integer sort;
    @TableField("creator")
    private Long creator;
    @TableField("created_at")
    private LocalDateTime createdAt;
    @TableField("last_updater")
    private Long lastUpdater;
    @TableField("last_updated_at")
    private LocalDateTime lastUpdatedAt;

}
