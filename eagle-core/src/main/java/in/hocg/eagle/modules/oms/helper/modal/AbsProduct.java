package in.hocg.eagle.modules.oms.helper.modal;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2019-10-14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbsProduct implements Product {


    public AbsProduct() {
        this.preDiscountPrice = BigDecimal.ZERO;
    }

    public AbsProduct(BigDecimal preDiscountPrice) {
        this.preDiscountPrice = preDiscountPrice;
    }

    /**
     * 优惠前价格
     */
    private BigDecimal preDiscountPrice;

    /**
     * 优惠的金额
     */
    private BigDecimal discountPrice = BigDecimal.ZERO;

    /**
     * 使用的优惠券
     */
    private List<Discount> useDiscount = Lists.newArrayList();


    @Override
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Override
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    @Override
    public BigDecimal getPreDiscountPrice() {
        return this.preDiscountPrice;
    }

    @Override
    public BigDecimal getPreferentialPrice() {
        return this.preDiscountPrice.subtract(discountPrice);
    }

    @Override
    public List<Discount> getUseDiscount() {
        return this.useDiscount;
    }

    @Override
    public void addUseDiscount(Discount discount) {
        this.useDiscount.add(discount);
    }
}
