package in.hocg.eagle.modules.oms.helper.order2.modal;

import com.google.common.collect.Maps;
import in.hocg.eagle.modules.oms.helper.order2.discount.Discount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface OrderContext<T extends Product> {

    /**
     * 添加订单所使用的优惠及额度
     *
     * @param discount
     * @param discountAmount
     */
    void addUseDiscount(Discount discount, BigDecimal discountAmount);

    List<T> getProducts();

    /**
     * 最终应支付金额
     *
     * @return
     */
    default BigDecimal getPaymentAmount() {
        return getProductTotalRealAmount().add(getSurchargeAmount())
            .subtract(getDiscountAmount());
    }

    /**
     * 获取未参与任何优惠的总金额
     *
     * @return
     */
    default BigDecimal getTotalAmount() {
        return getProductTotalAmount().add(getSurchargeAmount());
    }

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
     * 附加费
     *
     * @return
     */
    BigDecimal getSurchargeAmount();

    /**
     * 优惠金额
     *
     * @return
     */
    BigDecimal getDiscountAmount();

    /**
     * 获取总优惠金额
     *
     * @return
     */
    default BigDecimal getTotalDiscountAmount() {
        return getDiscountAmount().add(getProductTotalDiscountAmount());
    }

    /**
     * 获取商品原总金额
     *
     * @return
     */
    default BigDecimal getProductTotalAmount() {
        return getProducts().parallelStream().map(Product::getTotalAmount)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    /**
     * 获取商品优惠的金额之和
     *
     * @return
     */
    default BigDecimal getProductTotalDiscountAmount() {
        return getProductTotalDiscountAmount(getProducts());
    }

    /**
     * 获取商品优惠后的金额之和
     *
     * @return
     */
    default BigDecimal getProductTotalRealAmount() {
        return getProductTotalRealAmount(getProducts());
    }

    /**
     * 获取指定商品优惠后的金额之和
     *
     * @param products
     * @return
     */
    default BigDecimal getProductTotalRealAmount(List<T> products) {
        return products.parallelStream().map(Product::getRealAmount)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    /**
     * 获取指定商品优惠的金额之和
     *
     * @param products
     * @return
     */
    default BigDecimal getProductTotalDiscountAmount(List<T> products) {
        return products.parallelStream().map(Product::getDiscountAmount)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    /**
     * 订单所使用的优惠及额度
     *
     * @return
     */
    Map<Discount, BigDecimal> getUseDiscount();

    /**
     * 订单和商品所使用的优惠及额度
     *
     * @return
     */
    default Map<Discount, BigDecimal> getAllUseDiscount() {
        Map<Discount, BigDecimal> result = Maps.newHashMap();
        // 商品优惠
        for (T product : getProducts()) {
            for (Map.Entry<Discount, BigDecimal> entry : product.getUseDiscount().entrySet()) {
                final Discount key = entry.getKey();
                final BigDecimal value = entry.getValue();
                result.compute(key, (discount, bigDecimal) -> value.add(Objects.isNull(bigDecimal) ? BigDecimal.ZERO : bigDecimal));
            }
        }

        // 订单优惠
        for (Map.Entry<Discount, BigDecimal> entry : getUseDiscount().entrySet()) {
            final Discount key = entry.getKey();
            final BigDecimal value = entry.getValue();
            result.compute(key, (discount, bigDecimal) -> value.add(Objects.isNull(bigDecimal) ? BigDecimal.ZERO : bigDecimal));
        }
        return result;
    }
}
