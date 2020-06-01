package in.hocg.eagle.modules.bmw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * [支付网关] 退款记录表
 * </p>
 *
 * @author hocgin
 * @since 2020-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bmw_payment_refund")
public class PaymentRefund extends AbstractEntity<PaymentRefund> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("接入方应用ID")
    @TableField("app_id")
    private Long appId;
    @ApiModelProperty("接入方应用退款编号")
    @TableField("app_refund_sn")
    private String appRefundSn;
    @ApiModelProperty("退款流水编号")
    @TableField("refund_sn")
    private String refundSn;
    @ApiModelProperty("交易流水号")
    @TableField("transaction_sn")
    private String transactionSn;
    @ApiModelProperty("第三方退款的流水号")
    @TableField("refund_trade_no")
    private String refundTradeNo;
    @ApiModelProperty("退款金额")
    @TableField("refund_fee")
    private BigDecimal refundFee;
    @ApiModelProperty("退款理由")
    @TableField("refund_reason")
    private String refundReason;
    @ApiModelProperty("币种: CNY USD HKD")
    @TableField("currency_code")
    private String currencyCode;
    @ApiModelProperty("退款状态: 0=>未退款; 1=>退款处理中; 2=>退款成功; 3=>退款失败")
    @TableField("refund_status")
    private Integer refundStatus;
    @ApiModelProperty("支付渠道")
    @TableField("payment_way")
    private Integer paymentWay;
    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建ip")
    @TableField("created_ip")
    private String createdIp;
    @ApiModelProperty("更新时间")
    @TableField("updated_at")
    private LocalDateTime updatedAt;
    @ApiModelProperty("更新ip")
    @TableField("update_ip")
    private String updateIp;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
