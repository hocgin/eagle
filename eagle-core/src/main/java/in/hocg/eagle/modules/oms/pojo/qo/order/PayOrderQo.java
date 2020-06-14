package in.hocg.eagle.modules.oms.pojo.qo.order;

import in.hocg.web.constant.datadict.PaymentWay;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.web.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/3/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayOrderQo extends IdQo {
    @NotNull(message = "请选择正确的支付方式")
    @EnumRange(enumClass = PaymentWay.class, message = "请选择正确的支付方式")
    @ApiModelProperty("支付类型")
    private Integer paymentWay;
}
