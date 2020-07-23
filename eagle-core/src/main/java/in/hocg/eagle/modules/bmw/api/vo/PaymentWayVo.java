package in.hocg.eagle.modules.bmw.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/7/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class PaymentWayVo {
    @ApiModelProperty("支付渠道码")
    private Integer paymentWayCode;
    @ApiModelProperty("支付渠道名称")
    private String title;
}
