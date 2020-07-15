package in.hocg.eagle.modules.oms.helper.order2.discount;

import in.hocg.eagle.modules.oms.helper.order2.handler.FixedScaleDiscountHandler;
import in.hocg.eagle.modules.oms.helper.order2.modal.OrderContext;
import in.hocg.eagle.modules.oms.helper.order2.modal.Product;
import in.hocg.eagle.modules.oms.helper.order2.rule.Rule;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbsFixedScaleDiscount<T extends OrderContext, P extends Product> extends AbsDiscount<T, P> {
    @Getter
    private BigDecimal scale;

    @Getter
    @Setter
    private BigDecimal discountLimit;

    public AbsFixedScaleDiscount(BigDecimal scale, List<Rule> rules) {
        super(rules, new FixedScaleDiscountHandler());
        this.scale = scale;
        this.discountLimit = discountLimit;
    }
}
