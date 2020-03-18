package in.hocg.eagle.basic.calc;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.calc.modal.Discount;
import in.hocg.eagle.basic.calc.modal.Order;

import java.util.Comparator;
import java.util.List;

/**
 * Created by hocgin on 2019-10-14.
 * email: hocgin@gmail.com
 * - 最优优惠自动匹配
 * - 价格自动计算
 * -
 * <p>
 * <p>
 * -----
 * 1. 优惠券合法性校验
 * 2. 价格计算
 * 3. 优惠券优惠金额分配到商品
 *
 * @author hocgin
 */
public abstract class OrderHelper<O extends Order> {

    /**
     * 匹配最优
     */
    private List<AutoDiscountMatcher<O, ? extends Discount<?, ?>>> autoDiscountMatchers = Lists.newArrayList();

    public OrderHelper setAutoDiscountMatchers(List<AutoDiscountMatcher<O, ? extends Discount<?, ?>>> autoDiscountMatchers) {
        this.autoDiscountMatchers = autoDiscountMatchers;
        return this;
    }

    /**
     * 计算订单价格
     *
     * @param order     订单模块
     * @param discounts 使用的折扣
     * @return
     */
    public O useDiscounts(O order,
                          List<Discount> discounts) {
        discounts.stream()
            .sorted(Comparator.comparingInt(Discount::order))
            .forEach(order::use);
        return order;
    }

    /**
     * 计算订单价格
     *
     * @param order             订单模块
     * @param autoMatchDiscount 使用的折扣
     * @return
     */
    public O useDiscounts(O order, boolean autoMatchDiscount) {
        List<Discount> matchedDiscounts = Lists.newArrayList();
        if (autoMatchDiscount) {
            matchedDiscounts = getAutoMatchUsableDiscount(order);
        }
        return useDiscounts(order, matchedDiscounts);
    }

    /**
     * 使用自动匹配获取可用优惠
     *
     * @param order
     * @return
     */
    private List<Discount> getAutoMatchUsableDiscount(O order) {
        List<Discount> matchedDiscounts = Lists.newArrayList();
        for (AutoDiscountMatcher matcher : this.autoDiscountMatchers) {
            List<Discount> discount = matcher.match(order, matchedDiscounts);
            matchedDiscounts.addAll(discount);
        }
        return matchedDiscounts;
    }

    /**
     * 获取订单可用优惠券
     *
     * @param order
     * @param allDiscounts
     * @return
     */
    public List<Discount> getUsableDiscount(O order, List<Discount> allDiscounts) {
        return order.getUsableDiscount(allDiscounts);
    }

}

