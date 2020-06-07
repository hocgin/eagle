package in.hocg.eagle.modules.bmw.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import in.hocg.eagle.basic.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * [支付网关] 退款记录表
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bmw_refund_record")
public class RefundRecord extends AbstractEntity<RefundRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("接入方应用")
    @TableField("app_id")
    private Long appId;
    @ApiModelProperty("退款单号(接入方)")
    @TableField("out_refund_sn")
    private String outRefundSn;
    @ApiModelProperty("交易单号(网关)")
    @TableField("trade_sn")
    private String tradeSn;
    @ApiModelProperty("退款单号(网关)")
    @TableField("refund_sn")
    private String refundSn;
    @ApiModelProperty("退款单号(第三方)")
    @TableField("refund_trade_no")
    private String refundTradeNo;
    @ApiModelProperty("申请退款金额")
    @TableField("refund_fee")
    private BigDecimal refundFee;
    @ApiModelProperty("退款理由")
    @TableField("refund_reason")
    private String refundReason;
    @ApiModelProperty("退款状态: 0=>未退款; 1=>退款处理中; 2=>退款成功; 3=>退款失败")
    @TableField("refund_status")
    private Integer refundStatus;
    @ApiModelProperty("通知接入应用的地址")
    @TableField("notify_url")
    private String notifyUrl;
    @ApiModelProperty("实际退款金额")
    @TableField("settlement_refund_fee")
    private BigDecimal settlementRefundFee;
    @ApiModelProperty("最终第三方退款成功的时间(第三方回调填充)")
    @TableField("refund_at")
    private LocalDateTime refundAt;
    @ApiModelProperty("接收到第三方支付通知的时间")
    @TableField("notify_at")
    private LocalDateTime notifyAt;
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
