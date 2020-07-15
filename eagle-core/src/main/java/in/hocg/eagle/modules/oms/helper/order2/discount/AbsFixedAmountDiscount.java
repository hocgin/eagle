package in.hocg.eagle.modules.oms.helper.order2.discount;


import in.hocg.eagle.modules.oms.helper.order2.handler.FixedAmountDiscountHandler;
import in.hocg.eagle.modules.oms.helper.order2.modal.OrderContext;
import in.hocg.eagle.modules.oms.helper.order2.modal.Product;
import in.hocg.eagle.modules.oms.helper.order2.rule.Rule;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbsFixedAmountDiscount<T extends OrderContext, P extends Product> extends AbsDiscount<T, P> {
    @Getter
    private BigDecimal val;

    public AbsFixedAmountDiscount(BigDecimal val, List<Rule> rules) {
        super(rules, new FixedAmountDiscountHandler());
        this.val = val;
    }

}
