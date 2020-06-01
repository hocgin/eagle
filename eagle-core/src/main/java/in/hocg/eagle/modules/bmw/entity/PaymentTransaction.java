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
 * [支付网关] 交易流水表
 * </p>
 *
 * @author hocgin
 * @since 2020-05-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bmw_payment_transaction")
public class PaymentTransaction extends AbstractEntity<PaymentTransaction> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("接入应用")
    @TableField("app_id")
    private Long appId;
    @ApiModelProperty("接入应用订单号")
    @TableField("app_order_sn")
    private String appOrderSn;
    @ApiModelProperty("交易流水号")
    @TableField("transaction_sn")
    private String transactionSn;
    @ApiModelProperty("交易总金额")
    @TableField("total_fee")
    private BigDecimal totalFee;
    @ApiModelProperty("支付选择的币种，CNY、HKD、USD等")
    @TableField("currency_code")
    private String currencyCode;
    @ApiModelProperty("交易状态: 0=>等待支付; 1=>待付款完成; 2=>完成支付; 3=>交易已关闭; 4=>支付失败")
    @TableField("trade_status")
    private Integer tradeStatus;
    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建的IP")
    @TableField("created_ip")
    private String createdIp;
    @ApiModelProperty("更新时间")
    @TableField("updated_at")
    private LocalDateTime updatedAt;
    @ApiModelProperty("更新的IP")
    @TableField("updated_ip")
    private String updatedIp;
    @ApiModelProperty("通知接入应用并完成交易的时间")
    @TableField("finish_at")
    private LocalDateTime finishAt;
    @ApiModelProperty("订单过期时间")
    @TableField("expire_at")
    private LocalDateTime expireAt;
    @ApiModelProperty("接收到第三方支付通知的时间")
    @TableField("notify_at")
    private LocalDateTime notifyAt;
    @ApiModelProperty("第三方支付成功的时间")
    @TableField("payment_at")
    private LocalDateTime paymentAt;
    @ApiModelProperty("支付方式")
    @TableField("payment_way")
    private Integer paymentWay;
    @ApiModelProperty("第三方的流水号")
    @TableField("trade_no")
    private String tradeNo;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
