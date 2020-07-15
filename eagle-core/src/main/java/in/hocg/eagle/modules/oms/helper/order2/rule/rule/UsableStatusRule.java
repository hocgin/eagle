package in.hocg.eagle.modules.oms.helper.order2.rule.rule;


import in.hocg.eagle.basic.constant.datadict.CouponUseStatus;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order2.rule.AbsRule;
import in.hocg.eagle.modules.oms.helper.order2.rule.CheckResult;

import java.util.List;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 * 订单最低启用规则
 *
 * @author hocgin
 */
public class UsableStatusRule extends AbsRule<GeneralOrder> {
    private final Integer useStatus;

    public UsableStatusRule(Integer useStatus) {
        this.useStatus = useStatus;
    }

    @Override
    public CheckResult check(GeneralOrder context) {
        final List<GeneralProduct> allProducts = context.getProducts();
        if (CouponUseStatus.Unused.eq(useStatus)) {
            return success(allProducts);
        }
        return fail(allProducts, "优惠券状态不符合");
    }
}
