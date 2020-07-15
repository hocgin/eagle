package in.hocg.eagle.modules.oms.helper.order2.rule.rule;


import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order2.rule.AbsRule;
import in.hocg.eagle.modules.oms.helper.order2.rule.CheckResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 * 订单最低启用规则
 *
 * @author hocgin
 */
public class OrderMinPointRule extends AbsRule<GeneralOrder> {
    private final BigDecimal minPoint;

    public OrderMinPointRule(BigDecimal minPoint) {
        this.minPoint = minPoint;
    }

    @Override
    public CheckResult check(GeneralOrder context) {
        final List<GeneralProduct> allProducts = context.getProducts();
        final BigDecimal productTotalAmount = context.getProductTotalAmount();
        if (minPoint.compareTo(productTotalAmount) <= 0) {
            return success(allProducts);
        }
        return fail(allProducts, "订单不符合最低启用金额");
    }
}
