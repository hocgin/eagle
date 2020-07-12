package in.hocg.eagle.modules.oms.helper.order.discount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/7/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class DiscountComplex {
    private Long id;
    private String title;
    private String remark;
    @ApiModelProperty("优惠方式: 0=>立减;1=>折扣")
    private Type type;
    private BigDecimal val;

    enum Type {
        Reduction,
        Discount
    }
}
