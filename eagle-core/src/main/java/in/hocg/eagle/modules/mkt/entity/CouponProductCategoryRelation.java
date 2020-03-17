package in.hocg.eagle.modules.mkt.entity;

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
 * 优惠券可用商品品类表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mkt_coupon_product_category_relation")
public class CouponProductCategoryRelation extends AbstractEntity<CouponProductCategoryRelation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 优惠券ID
     */
    @TableField("coupon_id")
    private Long couponId;
    /**
     * 产品ID
     */
    @TableField("product_category_id")
    private Long productCategoryId;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
