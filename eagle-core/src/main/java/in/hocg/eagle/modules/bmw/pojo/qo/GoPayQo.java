package in.hocg.eagle.modules.bmw.pojo.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by hocgin on 2020/5/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Builder
public class GoPayQo {
    @ApiModelProperty(value = "交易流水号", required = true)
    private final String transactionSn;
    @ApiModelProperty(value = "支付方式", required = true)
    private final Integer paymentWay;
    @ApiModelProperty(value = "[可选]微信支付")
    private String wxOpenId;
}
