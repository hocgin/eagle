package in.hocg.eagle.modules.mkt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券账号拥有人表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mkt_coupon_account")
public class CouponAccount extends AbstractEntity<CouponAccount> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 拥有人
     */
    @TableField("account_id")
    private Long accountId;
    /**
     * mkt_COUPON ID
     */
    @TableField("coupon_id")
    private Long couponId;
    /**
     * 优惠券编号
     */
    @TableField("coupon_sn")
    private String couponSn;
    /**
     * 实际抵扣金额
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;
    /**
     * 使用状态：[0:未使用；1:已使用；2:已过期]
     */
    @TableField("use_status")
    private Integer useStatus;
    /**
     * 优惠券使用时间
     */
    @TableField("used_at")
    private LocalDateTime usedAt;
    /**
     * 优惠券生效时间
     */
    @TableField("start_at")
    private LocalDateTime startAt;
    /**
     * 优惠券失效时间
     */
    @TableField("end_at")
    private LocalDateTime endAt;
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
