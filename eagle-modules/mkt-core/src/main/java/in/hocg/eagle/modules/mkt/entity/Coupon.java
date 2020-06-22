package in.hocg.eagle.modules.mkt.entity;

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
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mkt_coupon")
public class Coupon extends AbstractEntity<Coupon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 优惠券名称
     */
    @TableField("title")
    private String title;
    /**
     * 使用方式：[0:满减；1:折扣]
     */
    @TableField("coupon_type")
    private Integer couponType;
    /**
     * 优惠券使用说明
     */
    @TableField("instructions")
    private String instructions;
    /**
     * 优惠券备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 最低启用金额
     */
    @TableField("min_point")
    private BigDecimal minPoint;
    /**
     * 满减金额/折扣率
     */
    @TableField("credit")
    private BigDecimal credit;
    /**
     * 优惠上限金额
     */
    @TableField("ceiling")
    private BigDecimal ceiling;
    /**
     * 可用平台：[0:全部；1:移动；2:PC]
     */
    @TableField("platform")
    private Integer platform;
    /**
     * 可用类型：[0:全场通用；1:指定品类；2:指定商品]
     */
    @TableField("useType")
    private Integer useType;
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
