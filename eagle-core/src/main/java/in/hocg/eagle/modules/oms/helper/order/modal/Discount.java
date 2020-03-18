package in.hocg.eagle.modules.oms.helper.order.modal;

import in.hocg.eagle.utils.LangUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hocgin on 2019-10-14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface Discount<T extends Order, P extends Product> {

    /**
     * 优惠标识
     * - 例如: 用户拥有的优惠券ID
     *
     * @return
     */
    default Serializable id() {
        return 0;
    }

    /**
     * 优惠名称
     *
     * @return
     */
    String title();

    /**
     * 获取使用该优惠券的商品
     *
     * @return
     */
    List<P> getUsedProduct();

    /**
     * 订单维度的匹配
     *
     * @param order
     * @return
     */
    default List<P> match(T order) {
        return new ArrayList<>();
    }

    /**
     * 获取使用该折扣进行优惠的总金额
     *
     * @return
     */
    BigDecimal getDiscountTotalAmount();

    /**
     * 包裹 handle 处理
     *
     * @param order
     */
    void handle(T order, List<P> usedProduct);

    /**
     * 处理优惠的具体实现
     * - 计算商品的折扣金额
     * - 计算课次的预结转金额
     *
     * @param order
     * @param usedProduct
     * @return 优惠总金额
     */
    BigDecimal _handle(T order, List<P> usedProduct);

    /**
     * 优惠券排序
     *
     * @return
     */
    default int order() {
        return 1;
    }

    /**
     * 打印信息
     *
     * @param spaceCount
     * @return
     */
    default String toStrings(int spaceCount) {
        String space = LangUtils.getSpace(spaceCount);
        return String.format("%s=>未知类型的优惠券", space);
    }
}
