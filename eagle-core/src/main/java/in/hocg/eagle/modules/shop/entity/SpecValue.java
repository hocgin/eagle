package in.hocg.eagle.modules.shop.entity;

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
 * [Shop模块] 商品规格值表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("shop_spec_value")
public class SpecValue extends AbstractEntity<SpecValue> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 规格(t_spec)ID
     */
    @TableField("spec_id")
    private Long specId;
    /**
     * 规格(t_sku)ID
     */
    @TableField("sku_id")
    private Long skuId;
    /**
     * 规格值(如: 红色)
     */
    @TableField("value")
    private String value;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
