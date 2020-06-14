package in.hocg.eagle.modules.oms.entity;

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
 * 订单退货申请
 * </p>
 *
 * @author hocgin
 * @since 2020-03-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_order_refund_apply")
public class OrderRefundApply extends AbstractEntity<OrderRefundApply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("apply_sn")
    private String applySn;
    /**
     * 申请状态：[0:待处理；1:退货中；2:已完成；3:已拒绝]
     */
    @TableField("apply_status")
    private Integer applyStatus;
    /**
     * 订单商品ID
     */
    @TableField("order_item_id")
    private Long orderItemId;
    /**
     * 退货数量
     */
    @TableField("refund_quantity")
    private Integer refundQuantity;
    /**
     * 退款金额
     */
    @TableField("refund_amount")
    private BigDecimal refundAmount;
    /**
     * 退货原因
     */
    @TableField("refund_reason")
    private String refundReason;
    /**
     * 退货备注
     */
    @TableField("refund_remark")
    private String refundRemark;
    /**
     * 仓库收货地址表ID
     */
    @TableField("company_address_id")
    private Long companyAddressId;
    /**
     * 处理人
     */
    @TableField("handler")
    private Long handler;
    /**
     * 处理时间
     */
    @TableField("handle_at")
    private LocalDateTime handleAt;
     /**
     * 处理备注
     */
    @TableField("handle_remark")
    private String handleRemark;
    /**
     * 收货人
     */
    @TableField("receiver")
    private Long receiver;
    /**
     * 收货时间
     */
    @TableField("receive_at")
    private LocalDateTime receiveAt;
    /**
     * 收货备注
     */
    @TableField("receive_remark")
    private String receiveRemark;
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
