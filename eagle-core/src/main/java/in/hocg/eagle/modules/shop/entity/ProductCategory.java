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
 * [Shop模块] 商品品类表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("shop_product_category")
public class ProductCategory extends AbstractEntity<ProductCategory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("parent_id")
    private Long parentId;
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
     * 树路径，组成方式: /父路径/当前ID
     */
    @TableField("tree_path")
    private String treePath;
    /**
     * 启用状态[0:为禁用状态;1:为正常状态]
     */
    @TableField("enabled")
    private Boolean enabled;
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
