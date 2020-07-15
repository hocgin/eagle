package in.hocg.eagle.modules.oms.helper.order2.rule;


import in.hocg.eagle.modules.oms.helper.order2.modal.OrderContext;
import in.hocg.eagle.modules.oms.helper.order2.modal.Product;

import java.util.List;

/**
 * Created by hocgin on 2020/7/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbsRule<T extends OrderContext> implements Rule<T> {

    private CheckResult result(boolean isOk) {
        return new CheckResult(isOk).setRule(this);
    }

    protected CheckResult success(List<? extends Product> conform) {
        return result(true).setConform(conform);
    }

    protected CheckResult fail(List<? extends Product> unConform, String reason) {
        return result(false).setReason(reason).setUnConform(unConform);
    }
}
