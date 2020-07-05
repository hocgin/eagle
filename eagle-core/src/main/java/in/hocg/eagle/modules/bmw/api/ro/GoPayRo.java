package in.hocg.eagle.modules.bmw.api.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@ApiModel("支付请求")
public class GoPayRo {
    @NotNull(message = "交易单号不能为空")
    @ApiModelProperty(value = "交易单号", required = true)
    private String tradeSn;
    @NotNull(message = "支付方式不能为空")
    @ApiModelProperty(value = "支付方式", required = true)
    private Integer paymentWay;
    @ApiModelProperty(value = "[可选]微信支付")
    private String wxOpenId;
}
