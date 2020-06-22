package in.hocg.eagle.modules.oms.helper.order.modal;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2019-10-14.
 * email: hocgin@gmail.com
 * - 该商品优惠前金额: 商品原价
 * - 该商品优惠后价格: 商品原价 - 优惠折扣
 *
 * @author hocgin
 */
public interface Product {

    /**
     * 该商品优惠前金额
     *
     * @return
     */
    BigDecimal getPreDiscountPrice();

    /**
     * 该商品优惠后价格
     *
     * @return
     */
    BigDecimal getPreferentialPrice();

    /**
     * 设置优惠的金额
     *
     * @param discountPrice
     */
    void setDiscountPrice(BigDecimal discountPrice);

    /**
     * 该商品折扣的金额
     *
     * @return
     */
    BigDecimal getDiscountPrice();

    /**
     * 固定费用金额
     * @return
     */
    default BigDecimal getFixedCostsPrice(){
        return BigDecimal.ZERO;
    }

    /**
     * 该商品使用的折扣
     *
     * @return
     */
    List<Discount> getUseDiscount();

    /**
     * 添加该商品使用的优惠券
     *
     * @param discount
     */
    void addUseDiscount(Discount discount);

}
