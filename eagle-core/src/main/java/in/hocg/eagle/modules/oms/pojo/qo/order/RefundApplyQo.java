package in.hocg.eagle.modules.oms.pojo.qo.order;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefundApplyQo extends BaseQo {
    @NotNull
    @ApiModelProperty("Id")
    private Long orderItemId;
    @NotNull
    @ApiModelProperty("退货原因")
    private String refundReason;
    @NotNull
    @ApiModelProperty("备注")
    private String refundRemark;
    @NotNull
    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;
}
