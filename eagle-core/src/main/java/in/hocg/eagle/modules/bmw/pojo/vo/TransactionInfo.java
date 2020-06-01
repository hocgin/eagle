package in.hocg.eagle.modules.bmw.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/6/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class TransactionInfo {
    @ApiModelProperty("接入应用订单号")
    private String appOrderSn;
    @ApiModelProperty("交易流水号")
    private String transactionSn;
    @ApiModelProperty("交易总金额")
    private BigDecimal totalFee;
    @ApiModelProperty("支付选择的币种，CNY、HKD、USD等")
    private String currencyCode;
    @ApiModelProperty("交易状态: 0=>等待支付; 1=>待付款完成; 2=>完成支付; 3=>交易已关闭; 4=>支付失败")
    private Integer tradeStatus;
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建的IP")
    private String createdIp;
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;
    @ApiModelProperty("更新的IP")
    private String updatedIp;
    @ApiModelProperty("通知接入应用并完成交易的时间")
    private LocalDateTime finishAt;
    @ApiModelProperty("订单过期时间")
    private LocalDateTime expireAt;
    @ApiModelProperty("接收到第三方支付通知的时间")
    private LocalDateTime notifyAt;
    @ApiModelProperty("第三方支付成功的时间")
    private LocalDateTime paymentAt;
    @ApiModelProperty("支付渠道ID")
    private Integer paymentWay;
    @ApiModelProperty("第三方的流水号")
    private String tradeNo;
}
