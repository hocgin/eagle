package in.hocg.eagle.modules.oms.pojo.qo.order;

import in.hocg.eagle.basic.constant.datadict.OrderPayType;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/3/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class PayOrderQo extends IdQo {
    @NotNull(message = "请选择正确的支付方式")
    @EnumRange(enumClass = OrderPayType.class, message = "请选择正确的支付方式")
    @ApiModelProperty("支付类型")
    private Integer payType;
}
