package in.hocg.eagle.modules.bmw.api.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class GoRefundRo {
    @ApiModelProperty("接入应用编号")
    private Long appSn;
    @ApiModelProperty("退款单号(接入方)")
    private String outRefundSn;
    @ApiModelProperty("交易单号(网关)")
    private String tradeSn;
    @ApiModelProperty("退款金额")
    private BigDecimal refundFee;
    @ApiModelProperty("退款理由")
    private String refundReason;
}
