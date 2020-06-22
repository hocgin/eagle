package in.hocg.eagle.modules.bmw.pojo.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@ApiModel("创建交易单")
public class CreateTradeRo {
    @NonNull
    @ApiModelProperty(value = "接入应用编号", required = true)
    private Long appSn;
    @NonNull
    @ApiModelProperty(value = "接入应用订单号", required = true)
    private String outTradeSn;
    @NonNull
    @ApiModelProperty(value = "交易总金额", required = true)
    private BigDecimal totalFee;
    @ApiModelProperty("通知地址")
    private String notifyUrl;
}
