package in.hocg.eagle.modules.oms.helper.order2.modal;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import in.hocg.eagle.modules.oms.helper.order2.discount.Discount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class AbsOrder<T extends Product> implements OrderContext<T> {

    public AbsOrder() {
        this(Lists.newArrayList());
    }

    public AbsOrder(List<T> products) {
        this.products = products;
    }

    /**
     * 包含的商品
     */
    private final List<T> products;
    /**
     * 附加费
     */
    private final Map<Surcharge, BigDecimal> surchargeMap = Maps.newHashMap();
    /**
     * <优惠对象,优惠金额>
     */
    private Map<Discount, BigDecimal> useDiscountMap = Maps.newHashMap();

    @Override
    public void addUseDiscount(Discount discount, BigDecimal discountAmount) {
        this.useDiscountMap.put(discount, discountAmount);
    }

    @Override
    public List<T> getProducts() {
        return this.products;
    }

    @Override
    public BigDecimal getSurchargeAmount() {
        return surchargeMap.values().parallelStream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getDiscountAmount() {
        return getUseDiscount().values().parallelStream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public Map<Discount, BigDecimal> getUseDiscount() {
        return this.useDiscountMap;
    }

}
