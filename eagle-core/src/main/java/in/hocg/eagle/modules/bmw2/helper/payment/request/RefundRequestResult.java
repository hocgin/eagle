package in.hocg.eagle.modules.bmw2.helper.payment.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/5/31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class RefundRequestResult {
    private Integer paymentWay;
    @ApiModelProperty("第三方退款的流水号")
    private String refundTradeNo;
}
