package in.hocg.eagle.modules.oms.helper.order2.rule.rule;


import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order2.rule.AbsRule;
import in.hocg.eagle.modules.oms.helper.order2.rule.CheckResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 * 指定品类
 *
 * @author hocgin
 */
public class UsableProductCategoryRule extends AbsRule<GeneralOrder> {
    private final List<Long> usableProductCategory;

    public UsableProductCategoryRule(List<Long> usableProductCategory) {
        this.usableProductCategory = usableProductCategory;
    }

    @Override
    public CheckResult check(GeneralOrder context) {
        final List<GeneralProduct> allProducts = context.getProducts();
        final List<GeneralProduct> conform = allProducts.parallelStream()
                .filter(generalProduct -> usableProductCategory.contains(generalProduct.getProductCategoryId()))
                .collect(Collectors.toList());
        if (!conform.isEmpty()) {
            return success(conform);
        }
        return fail(allProducts, "指定商品类型不满足");
    }
}
