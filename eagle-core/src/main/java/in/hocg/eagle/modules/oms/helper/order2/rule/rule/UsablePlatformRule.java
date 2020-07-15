package in.hocg.eagle.modules.oms.helper.order2.rule.rule;


import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order2.rule.AbsRule;
import in.hocg.eagle.modules.oms.helper.order2.rule.CheckResult;

import java.util.List;
import java.util.Objects;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 * 指定平台
 *
 * @author hocgin
 */
public class UsablePlatformRule extends AbsRule<GeneralOrder> {
    private final Integer platform;

    public UsablePlatformRule(Integer platform) {
        this.platform = platform;
    }

    @Override
    public CheckResult check(GeneralOrder context) {
        final List<GeneralProduct> allProducts = context.getProducts();
        if (Objects.equals(platform, context.getPlatform())) {
            return success(allProducts);
        }

        return fail(allProducts, "下单平台不符合");
    }
}
