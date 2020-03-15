package in.hocg.eagle.modules.oms.entity;

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
 * [订单模块] 订单主表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_order")
public class Order extends AbstractEntity<Order> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 账号ID
     */
    @TableField("account_id")
    private Long accountId;
    /**
     * 订单编号
     */
    @TableField("order_sn")
    private String orderSn;
    /**
     * 优惠券ID
     */
    @TableField("coupon_id")
    private Long couponId;
    /**
     * 优惠券抵扣金额
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;
    /**
     * 运费金额
     */
    @TableField("freight_amount")
    private BigDecimal freightAmount;
    /**
     * 管理员后台调整订单使用的折扣金额
     */
    @TableField("discount_amount")
    private BigDecimal discountAmount;
    /**
     * [计算型]订单总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;
    /**
     * [计算型]应付金额（实际支付金额）
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;
    /**
     * 自动确认时间（天）
     */
    @TableField("auto_confirm_day")
    private Integer autoConfirmDay;
    /**
     * 支付方式：[0:未支付；1:支付宝；2:微信]
     */
    @TableField("pay_type")
    private Integer payType;
    /**
     * 订单来源：[0:PC订单；1:APP订单]
     */
    @TableField("source_type")
    private Integer sourceType;
    /**
     * 订单状态：[0:待付款；1:待发货；2:已发货；3:已完成；4:已关闭；5:无效订单]
     */
    @TableField("order_status")
    private Integer orderStatus;
    /**
     * 确认收货状态：[0:未确认；1:已确认]
     */
    @TableField("confirm_status")
    private Integer confirmStatus;
    /**
     * 删除状态：[0:未删除；1:已删除]
     */
    @TableField("delete_status")
    private Integer deleteStatus;
    /**
     * 收货人姓名
     */
    @TableField("receiver_name")
    private String receiverName;
    /**
     * 收货人电话
     */
    @TableField("receiver_phone")
    private String receiverPhone;
    /**
     * 收货人邮编
     */
    @TableField("receiver_post_code")
    private String receiverPostCode;
    /**
     * 省份/直辖市
     */
    @TableField("receiver_province")
    private String receiverProvince;
    /**
     * 城市
     */
    @TableField("receiver_city")
    private String receiverCity;
    /**
     * 区
     */
    @TableField("receiver_region")
    private String receiverRegion;
    /**
     * 详细地址
     */
    @TableField("receiver_detail_address")
    private String receiverDetailAddress;
    /**
     * 订单备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 支付时间
     */
    @TableField("payment_time")
    private LocalDateTime paymentTime;
    /**
     * 发货时间
     */
    @TableField("delivery_time")
    private LocalDateTime deliveryTime;
    /**
     * 确认收货时间
     */
    @TableField("receive_time")
    private LocalDateTime receiveTime;
    /**
     * 评价时间
     */
    @TableField("comment_time")
    private LocalDateTime commentTime;
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
