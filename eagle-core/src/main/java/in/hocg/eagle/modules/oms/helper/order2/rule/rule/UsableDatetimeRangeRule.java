package in.hocg.eagle.modules.oms.helper.order2.rule.rule;


import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order2.rule.AbsRule;
import in.hocg.eagle.modules.oms.helper.order2.rule.CheckResult;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 * 指定商品
 *
 * @author hocgin
 */
public class UsableDatetimeRangeRule extends AbsRule<GeneralOrder> {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public UsableDatetimeRangeRule(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    @Override
    public CheckResult check(GeneralOrder context) {
        final LocalDateTime createAt = context.getCreateAt();
        boolean isUsableDate = startAt.isBefore(createAt)
                && createAt.isBefore(endAt);
        final List<GeneralProduct> allProducts = context.getProducts();
        if (isUsableDate) {
            return success(allProducts);
        }

        return fail(allProducts, "时间范围不符合");
    }
}
