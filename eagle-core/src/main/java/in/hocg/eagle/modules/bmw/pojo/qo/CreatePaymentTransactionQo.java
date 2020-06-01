package in.hocg.eagle.modules.bmw.pojo.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/5/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Builder
@ApiModel("创建支付流水")
public class CreatePaymentTransactionQo {
    @ApiModelProperty("接入应用")
    private final Long appId;
    @ApiModelProperty("接入应用订单号")
    private final String appOrderSn;
    @ApiModelProperty("交易总金额")
    private final BigDecimal totalFee;
    @ApiModelProperty("支付选择的币种，CNY、HKD、USD等")
    private String currencyCode;
}
