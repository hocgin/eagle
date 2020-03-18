package in.hocg.eagle.modules.oms.helper;


import in.hocg.eagle.modules.oms.helper.modal.Discount;
import in.hocg.eagle.modules.oms.helper.modal.Order;

import java.util.List;

/**
 * Created by hocgin on 2019-10-14.
 * email: hocgin@gmail.com
 * 自动匹配折扣处理器
 *
 * @author hocgin
 */
public interface AutoDiscountMatcher<O extends Order, T extends Discount> {

    /**
     * 匹配算法
     *
     * @param order
     * @param matchedDiscounts 已匹配的折扣
     * @return
     */
    List<T> match(O order, List<Discount> matchedDiscounts);
}
