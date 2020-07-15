package in.hocg.eagle.modules.oms.helper.order2.modal;

import com.google.common.collect.Maps;
import in.hocg.eagle.modules.oms.helper.order2.discount.Discount;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class AbsProduct implements Product {

    public AbsProduct() {
        this(BigDecimal.ZERO);
    }

    public AbsProduct(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 优惠前金额
     */
    private final BigDecimal totalPrice;
    /**
     * <优惠对象,优惠金额>
     */
    private Map<Discount, BigDecimal> useDiscountMap = Maps.newHashMap();

    @Override
    public void addUseDiscount(Discount discount, BigDecimal discountAmount) {
        this.useDiscountMap.put(discount, discountAmount);
    }

    @Override
    public BigDecimal getTotalAmount() {
        return this.totalPrice;
    }

    @Override
    public Map<Discount, BigDecimal> getUseDiscount() {
        return this.useDiscountMap;
    }
}
