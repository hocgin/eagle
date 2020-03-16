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
 * 订单退货申请
 * </p>
 *
 * @author hocgin
 * @since 2020-03-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_order_return_apply")
public class OrderReturnApply extends AbstractEntity<OrderReturnApply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
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
     * 商品图片
     */
    @TableField("product_pic")
    private String productPic;
    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;
    /**
     * 商品规格
     */
    @TableField("product_spec_data")
    private String productSpecData;
    /**
     * 商品单价
     */
    @TableField("product_price")
    private BigDecimal productPrice;
    /**
     * 商品实际支付价格(总价)
     */
    @TableField("product_real_amount")
    private BigDecimal productRealAmount;
    /**
     * 退货数量
     */
    @TableField("return_quantity")
    private Integer returnQuantity;
    /**
     * 退款金额
     */
    @TableField("return_amount")
    private BigDecimal returnAmount;
    /**
     * 退货原因
     */
    @TableField("return_reason")
    private String returnReason;
    /**
     * 退货备注
     */
    @TableField("return_remark")
    private String returnRemark;
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
     * 收货备注
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
