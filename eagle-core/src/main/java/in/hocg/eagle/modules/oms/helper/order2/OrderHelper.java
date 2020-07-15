package in.hocg.eagle.modules.oms.helper.order2;

import com.google.common.collect.Maps;
import in.hocg.eagle.modules.oms.helper.order2.discount.Discount;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order2.rule.CheckResult;
import in.hocg.eagle.modules.oms.helper.order2.rule.DiscountCheckResult;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class OrderHelper {

    public GeneralOrder use(GeneralOrder context, List<? extends Discount> discounts) {
        for (Discount discount : discounts) {
            discount.handle(context);
        }
        return context;
    }

    public Map<Discount, List<CheckResult>> check(GeneralOrder context, List<? extends Discount> discounts) {
        final Map<Discount, List<CheckResult>> result = Maps.newHashMap();
        for (Discount discount : discounts) {
            result.put(discount, discount.checks(context));
        }
        return result;
    }

    public Map<Discount, DiscountCheckResult> check2(GeneralOrder context, List<? extends Discount> discounts) {
        final Map<Discount, DiscountCheckResult> result = Maps.newHashMap();
        for (Discount discount : discounts) {
            result.put(discount, new DiscountCheckResult(discount, discount.checks(context)));
        }
        return result;
    }
}
