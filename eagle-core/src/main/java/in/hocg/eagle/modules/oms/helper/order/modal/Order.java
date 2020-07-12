package in.hocg.eagle.modules.oms.helper.order.modal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2019-10-14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface Order<T extends Product> {

    /**
     * 获取订单优惠前的价格
     *
     * @return
     */
    BigDecimal getPreDiscountTotalAmount();

    /**
     * 获取订单优惠后的价格
     *
     * @return
     */
    BigDecimal getPreferentialAmount();

    /**
     * 获取折扣的总金额
     *
     * @return
     */
    BigDecimal getDiscountTotalAmount();

    /**
     * 获取固定费用
     *
     * @return
     */
    BigDecimal getFixedCostsAmount();

    /**
     * 使用优惠
     *
     * @param discount
     * @return
     */
    Order use(Discount discount);

    /**
     * 获取商品列表
     *
     * @return
     */
    List<T> getProducts();

    /**
     * 获取该订单使用所有折扣
     *
     * @return
     */
    Set<Discount> getUseDiscount();

    /**
     * 批量处理 Product
     *
     * @param func
     * @param <R>
     * @return
     */
    default <R> List<R> mapProduct(Function<T, R> func) {
        return getProducts().stream().map(func).collect(Collectors.toList());
    }

    /**
     * 根据传入的优惠券获取该订单可用的优惠券
     *
     * @param allDiscounts
     * @return
     */
    List<Discount> getUsableDiscount(List<? extends Discount> allDiscounts);
}
