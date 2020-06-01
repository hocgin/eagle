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
    @ApiModelProperty("交易流水号")
    private final String transactionSn;
    @ApiModelProperty("支付方式")
    private final Integer paymentWay;
}
