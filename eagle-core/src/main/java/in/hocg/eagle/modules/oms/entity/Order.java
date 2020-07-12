package in.hocg.eagle.modules.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("账号ID")
    @TableField("account_id")
    private Long accountId;
    @ApiModelProperty("订单编号")
    @TableField("order_sn")
    private String orderSn;
    @ApiModelProperty("交易单号")
    @TableField("trade_sn")
    private String tradeSn;
    @ApiModelProperty("优惠券ID")
    @TableField("coupon_account_id")
    private Long couponAccountId;

    @TableField("coupon_discount_amount")
    @ApiModelProperty("优惠券抵扣金额")
    private BigDecimal couponDiscountAmount;
    @TableField("adjustment_discount_amount")
    @ApiModelProperty("后台调整优惠")
    private BigDecimal adjustmentDiscountAmount;
    @TableField("freight_amount")
    @ApiModelProperty("运费金额")
    private BigDecimal freightAmount;
    @TableField("total_amount")
    @ApiModelProperty("[计算型]订单原总金额")
    private BigDecimal totalAmount;
    @TableField("discount_amount")
    @ApiModelProperty("[计算型]优惠总金额(不含后台调整优惠)")
    private BigDecimal discountAmount;
    @TableField("pay_amount")
    @ApiModelProperty("[计算型]应付金额(实际支付金额)=订单原总金额-优惠总金额-后台调整优惠+运费金额")
    private BigDecimal payAmount;

    @ApiModelProperty("自动确认时间（天）")
    @TableField("auto_confirm_day")
    private Integer autoConfirmDay;
    @ApiModelProperty("支付方式：[0:未支付；1:支付宝；2:微信]")
    @TableField("pay_type")
    private Integer payType;
    @ApiModelProperty("订单来源：[0:PC订单；1:APP订单]")
    @TableField("source_type")
    private Integer sourceType;
    @ApiModelProperty("订单状态：[0:待付款；1:待发货；2:已发货；3:已完成；4:已关闭；5:无效订单]")
    @TableField("order_status")
    private Integer orderStatus;
    @ApiModelProperty("确认收货状态：[0:未确认；1:已确认]")
    @TableField("confirm_status")
    private Integer confirmStatus;
    @ApiModelProperty("删除状态：[0:未删除；1:已删除]")
    @TableField("delete_status")
    private Integer deleteStatus;

    @ApiModelProperty("收货人姓名")
    @TableField("receiver_name")
    private String receiverName;
    @ApiModelProperty("收货人电话")
    @TableField("receiver_phone")
    private String receiverPhone;
    @ApiModelProperty("收货人邮编")
    @TableField("receiver_post_code")
    private String receiverPostCode;
    @ApiModelProperty("收货人省份/直辖市")
    @TableField("receiver_province")
    private String receiverProvince;
    @ApiModelProperty("收货人城市")
    @TableField("receiver_city")
    private String receiverCity;
    @ApiModelProperty("收货人区")
    @TableField("receiver_region")
    private String receiverRegion;
    @TableField("receiver_ad_code")
    @ApiModelProperty("收货人区域编码")
    private String receiverAdCode;
    @ApiModelProperty("收货人详细地址")
    @TableField("receiver_detail_address")
    private String receiverDetailAddress;

    @ApiModelProperty("订单备注")
    @TableField("remark")
    private String remark;
    @ApiModelProperty("支付时间")
    @TableField("payment_time")
    private LocalDateTime paymentTime;
    @ApiModelProperty("发货时间")
    @TableField("delivery_time")
    private LocalDateTime deliveryTime;
    @ApiModelProperty("确认收货时间")
    @TableField("receive_time")
    private LocalDateTime receiveTime;
    @ApiModelProperty("评价时间")
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
