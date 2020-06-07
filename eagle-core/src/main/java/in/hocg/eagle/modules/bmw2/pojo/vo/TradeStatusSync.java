package in.hocg.eagle.modules.bmw2.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@ApiModel("交易状态同步")
public class TradeStatusSync {
    @ApiModelProperty("支付方式")
    private Integer paymentWay;
    @ApiModelProperty("交易单号")
    private String tradeSn;
    @ApiModelProperty("商户单号")
    private String outTradeSn;
    @ApiModelProperty("交易状态")
    private Integer tradeStatus;
    @ApiModelProperty("交易金额")
    private BigDecimal totalFee;
    @ApiModelProperty("付款时间")
    private LocalDateTime paymentAt;
    @ApiModelProperty("(微信用户OPEN-ID)仅微信")
    private String openid;
}
