package in.hocg.eagle.modules.bmw.pojo.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/6/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Builder
public class GoRefundQo {
    @ApiModelProperty("APP ID")
    private final String appId;
    @ApiModelProperty("退款单号")
    private final String appRefundSn;
    @ApiModelProperty("交易流水号")
    private final String transactionSn;
    @ApiModelProperty("退款金额")
    private final BigDecimal refundFee;
    @ApiModelProperty("退款理由")
    private String refundReason;
    @ApiModelProperty("币种: CNY USD HKD")
    private String currencyCode;
}
