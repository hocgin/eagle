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
public class RefundInfo {
    @ApiModelProperty("接入方应用退款编号")
    private String appRefundSn;
    @ApiModelProperty("退款流水编号")
    private String refundSn;
    @ApiModelProperty("交易流水号")
    private String transactionSn;
    @ApiModelProperty("第三方退款的流水号")
    private String refundTradeNo;
    @ApiModelProperty("退款金额")
    private BigDecimal refundFee;
    @ApiModelProperty("退款理由")
    private String refundReason;
    @ApiModelProperty("币种: CNY USD HKD")
    private String currencyCode;
    @ApiModelProperty("退款状态: 0=>未退款; 1=>退款处理中; 2=>退款成功; 3=>退款失败")
    private Integer refundStatus;
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建ip")
    private String createdIp;
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;
    @ApiModelProperty("更新ip")
    private String updateIp;
}
