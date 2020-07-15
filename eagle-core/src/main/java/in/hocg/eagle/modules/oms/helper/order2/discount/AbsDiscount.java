package in.hocg.eagle.modules.oms.helper.order2.discount;


import com.google.common.collect.Lists;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.oms.helper.order2.handler.DefaultDiscountHandler;
import in.hocg.eagle.modules.oms.helper.order2.handler.DiscountHandler;
import in.hocg.eagle.modules.oms.helper.order2.modal.OrderContext;
import in.hocg.eagle.modules.oms.helper.order2.modal.Product;
import in.hocg.eagle.modules.oms.helper.order2.rule.CheckResult;
import in.hocg.eagle.modules.oms.helper.order2.rule.Rule;

import java.util.List;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbsDiscount<T extends OrderContext, P extends Product> implements Discount<T, P> {
    private Long id;
    private final List<Rule> rules;
    private final DiscountHandler handler;

    public AbsDiscount() {
        this(Lists.newArrayList(), new DefaultDiscountHandler());
    }

    public AbsDiscount(List<Rule> rules, DiscountHandler handler) {
        this.rules = rules;
        this.handler = handler;
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public Discount setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Discount<T, P> addRule(Rule rule) {
        rules.add(rule);
        return this;
    }

    @Override
    public List<CheckResult> checks(T context) {
        List<CheckResult> result = Lists.newArrayList();
        for (Rule<T> rule : rules) {
            result.add(rule.check(context));
        }
        return result;
    }

    @Override
    public T handle(T context) {
        final boolean isOk = this.isOk(context);
        if (!isOk) {
            throw ServiceException.wrap("优惠无法使用");
        }
        handler.handle(this, context, this.getConformProduct(context));
        return context;
    }

}
