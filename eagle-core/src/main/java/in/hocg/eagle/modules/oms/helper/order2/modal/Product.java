package in.hocg.eagle.modules.oms.helper.order2.modal;


import in.hocg.eagle.modules.oms.helper.order2.discount.Discount;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface Product {


    /**
     * 添加优惠 及 优惠金额
     *
     * @param discount
     * @param discountAmount
     */
    void addUseDiscount(Discount discount, BigDecimal discountAmount);

    /**
     * 优惠后总金额
     *
     * @return
     */
    default BigDecimal getRealAmount() {
        return getTotalAmount().subtract(this.getDiscountAmount());
    }

    /**
     * 原总金额
     *
     * @return
     */
    BigDecimal getTotalAmount();


    /**
     * 优惠额度
     *
     * @return
     */
    default BigDecimal getDiscountAmount() {
        return getUseDiscount().values().parallelStream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /**
     * 商品所使用的优惠及优惠额度
     *
     * @return
     */
    Map<Discount, BigDecimal> getUseDiscount();
}
