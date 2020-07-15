package in.hocg.eagle.modules.oms.helper.order2.handler;


import in.hocg.eagle.modules.oms.helper.order2.discount.Discount;
import in.hocg.eagle.modules.oms.helper.order2.modal.OrderContext;
import in.hocg.eagle.modules.oms.helper.order2.modal.Product;

import java.util.List;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface DiscountHandler<D extends Discount, T extends OrderContext, P extends Product> {

    default void handle(D discount, T context, List<P> conformProduct) {

    }

}
