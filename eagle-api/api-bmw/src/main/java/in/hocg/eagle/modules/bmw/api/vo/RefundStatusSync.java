package in.hocg.eagle.modules.bmw.api.vo;

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
@ApiModel("退费状态同步")
public class RefundStatusSync {
    @ApiModelProperty("交易单号")
    private String tradeSn;
    @ApiModelProperty("商户单号")
    private String outTradeSn;
    @ApiModelProperty("交易金额")
    private BigDecimal totalFee;

    @ApiModelProperty("退费单号")
    private String refundSn;
    @ApiModelProperty("商户退费单号")
    private String outRefundSn;
    @ApiModelProperty("退费状态")
    private Integer refundStatus;
    @ApiModelProperty("退款时间")
    private LocalDateTime refundAt;
    @ApiModelProperty("(微信用户OPEN-ID)仅微信")
    private String openid;
    @ApiModelProperty("申请退款金额")
    private BigDecimal refundFee;
    @ApiModelProperty("实际退款金额")
    private BigDecimal settlementRefundFee;
}
