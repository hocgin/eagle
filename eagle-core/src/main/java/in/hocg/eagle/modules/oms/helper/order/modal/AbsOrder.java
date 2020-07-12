package in.hocg.eagle.modules.oms.helper.order.modal;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.exception.ServiceException;

import java.math.BigDecimal;
import java.util.*;


/**
 * Created by hocgin on 2019-10-14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbsOrder<T extends Product>
    implements Order<T> {

    /**
     * 包含的商品
     */
    private final List<T> products;

    public AbsOrder() {
        this(Lists.newArrayList());
    }

    public AbsOrder(List<T> products) {
        this.products = products;
    }

    @Override
    public BigDecimal getPreDiscountTotalAmount() {
        // 商品优惠前金额
        return products.stream()
            .map(Product::getPreDiscountPrice)
            .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getPreferentialAmount() {
        // 商品优惠前金额 - 优惠折扣
        return getPreDiscountTotalAmount()
            .subtract(getDiscountTotalAmount());
    }

    @Override
    public BigDecimal getFixedCostsAmount() {
        return products.stream()
            .map(Product::getFixedCostsPrice)
            .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public Order use(Discount discount) {
        final List products = discount.match(this);
        if (products.isEmpty()) {
            throw ServiceException.wrap(discount.title() + "不满足使用条件");
        }
        discount.handle(this, products);
        return this;
    }

    @Override
    public List<T> getProducts() {
        return products;
    }

    @Override
    public Set<Discount> getUseDiscount() {
        Set<Discount> usedDiscount = new HashSet<>();
        for (Product product : products) {
            usedDiscount.addAll(product.getUseDiscount());
        }
        return usedDiscount;
    }

    @Override
    public BigDecimal getDiscountTotalAmount() {
        return products.stream()
            .map(Product::getDiscountPrice)
            .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public List<Discount> getUsableDiscount(List<? extends Discount> allDiscounts) {
        List<Discount> usableDiscounts = Lists.newArrayList();
        for (Discount discount : allDiscounts) {
            List<Product> products = discount.match(this);
            if (!products.isEmpty()) {
                usableDiscounts.add(discount);
            }
        }
        return usableDiscounts;
    }
}
