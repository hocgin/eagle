package in.hocg.eagle.modules.oms.helper.order2.discount.coupon;


import in.hocg.eagle.modules.oms.helper.order2.discount.AbsFixedScaleDiscount;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order2.rule.Rule;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2020/3/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class FixedScaleCoupon extends AbsFixedScaleDiscount<GeneralOrder, GeneralProduct>
        implements Coupon {

    public FixedScaleCoupon(BigDecimal scale, BigDecimal discountLimit, List<Rule> rules) {
        super(scale, rules);
        setDiscountLimit(discountLimit);
    }
}
