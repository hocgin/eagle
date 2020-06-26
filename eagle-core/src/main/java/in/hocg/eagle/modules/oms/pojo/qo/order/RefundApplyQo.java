package in.hocg.eagle.modules.oms.pojo.qo.order;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefundApplyQo extends BaseRo {
    @NotNull
    @ApiModelProperty("Id")
    private Long orderItemId;
    @NotNull
    @ApiModelProperty("退货原因")
    private String refundReason;
    @NotNull
    @ApiModelProperty("备注")
    private String refundRemark;
}
