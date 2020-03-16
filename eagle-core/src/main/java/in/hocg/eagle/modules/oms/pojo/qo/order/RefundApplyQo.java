package in.hocg.eagle.modules.oms.pojo.qo.order;

import in.hocg.eagle.basic.pojo.qo.IdQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefundApplyQo extends IdQo {
    @ApiModelProperty("退货原因")
    private String returnReason;
    @ApiModelProperty("退货数量")
    private String returnQuantity;
    @ApiModelProperty("备注")
    private String returnRemark;
    @ApiModelProperty("退款金额")
    private BigDecimal returnAmount;
}
