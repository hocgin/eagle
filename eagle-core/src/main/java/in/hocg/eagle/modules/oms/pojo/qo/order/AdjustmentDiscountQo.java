package in.hocg.eagle.modules.oms.pojo.qo.order;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/7/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AdjustmentDiscountQo extends BaseRo {
    @NotNull
    @Min(value = 0)
    private BigDecimal adjustmentDiscountAmount;
}
