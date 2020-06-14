package in.hocg.eagle.modules.bmw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.web.AbstractEntity;
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
 * @since 2020-06-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bmw_payment_trade")
public class PaymentTrade extends AbstractEntity<PaymentTrade> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("接入方应用")
    @TableField("app_id")
    private Long appId;
    @ApiModelProperty("交易单号(接入方)")
    @TableField("out_trade_sn")
    private String outTradeSn;
    @ApiModelProperty("交易单号(网关)")
    @TableField("trade_sn")
    private String tradeSn;
    @ApiModelProperty("交易总金额")
    @TableField("total_fee")
    private BigDecimal totalFee;
    @ApiModelProperty("买家实际支付金额")
    @TableField("buyer_pay_fee")
    private BigDecimal buyerPayFee;
    @ApiModelProperty("交易状态: 0=>等待支付; 1=>待付款; 2=>完成支付; 3=>交易已关闭; 4=>支付失败")
    @TableField("trade_status")
    private Integer tradeStatus;
    @ApiModelProperty("通知接入应用的地址")
    @TableField("notify_url")
    private String notifyUrl;
    @ApiModelProperty("最终支付平台ID(第三方回调时填充)")
    @TableField("payment_platform_id")
    private Long paymentPlatformId;
    @ApiModelProperty("最终支付方式(第三方回调时填充)")
    @TableField("payment_way")
    private Integer paymentWay;
    @ApiModelProperty("最终第三方的交易单号(第三方回调填充)")
    @TableField("trade_no")
    private String tradeNo;
    @ApiModelProperty("最终微信用户(仅微信支付)(第三方回调填充)")
    @TableField("wx_openid")
    private String wxOpenid;
    @ApiModelProperty("最终第三方支付成功的时间(第三方回调填充)")
    @TableField("payment_at")
    private LocalDateTime paymentAt;
    @ApiModelProperty("接收到第三方支付通知的时间")
    @TableField("notify_at")
    private LocalDateTime notifyAt;
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
    @ApiModelProperty("完成交易的时间")
    @TableField("finish_at")
    private LocalDateTime finishAt;
    @ApiModelProperty("订单过期时间")
    @TableField("expire_at")
    private LocalDateTime expireAt;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
