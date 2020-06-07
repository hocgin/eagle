package in.hocg.eagle.modules.bmw2.pojo.ro;

import in.hocg.eagle.basic.constant.datadict.RefundStatus;
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
public class RefundMessageRo {
    @ApiModelProperty(value = "支付平台唯一标识", required = true)
    private String appid;
    @ApiModelProperty(value = "支付平台", required = true)
    private Integer channel;
    @ApiModelProperty(value = "退款单号(第三方)", required = true)
    private String refundTradeNo;
    @ApiModelProperty(value = "退款单号(网关)", required = true)
    private String refundSn;
    @ApiModelProperty(value = "付款时间", required = true)
    private LocalDateTime refundAt;
    @ApiModelProperty("申请退款金额")
    private BigDecimal refundFee;
    @ApiModelProperty("实际退款金额")
    private BigDecimal settlementRefundFee;
    @ApiModelProperty("退款状态: 0=>未退款; 1=>退款处理中; 2=>退款成功; 3=>退款失败")
    private RefundStatus refundStatus;
}
