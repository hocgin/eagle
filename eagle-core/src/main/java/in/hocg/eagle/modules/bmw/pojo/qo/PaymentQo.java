package in.hocg.eagle.modules.bmw.pojo.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/6/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class PaymentQo {
    @NotBlank(message = "交易流水号不能为空")
    @ApiModelProperty("交易流水号")
    private String transactionSn;
    @NotNull(message = "支付方式不能为空")
    @ApiModelProperty("支付方式")
    private Integer paymentWay;
}
