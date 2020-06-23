package in.hocg.eagle.modules.bmw.api.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

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
    @NonNull
    @ApiModelProperty(value = "交易单号", required = true)
    private String tradeSn;
    @NonNull
    @ApiModelProperty(value = "支付方式", required = true)
    private Integer paymentWay;
    @ApiModelProperty(value = "[可选]微信支付")
    private String wxOpenId;
}
