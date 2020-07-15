package in.hocg.eagle.modules.oms.helper.order2.discount;


import in.hocg.eagle.modules.oms.helper.order2.modal.OrderContext;
import in.hocg.eagle.modules.oms.helper.order2.modal.Product;
import in.hocg.eagle.modules.oms.helper.order2.rule.CheckResult;
import in.hocg.eagle.modules.oms.helper.order2.rule.Rule;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface Discount<T extends OrderContext, P extends Product> {

    default Long id() {
        throw new UnsupportedOperationException("未设置ID");
    }

    Discount<T, P> setId(Long id);

    /**
     * 添加规则
     *
     * @param rule
     * @return
     */
    Discount<T, P> addRule(Rule rule);

    /**
     * 检查规则
     *
     * @param context
     * @return
     */
    List<CheckResult> checks(T context);

    /**
     * 是否可用
     *
     * @param context
     * @return
     */
    default boolean isOk(T context) {
        return this.checks(context).parallelStream().allMatch(CheckResult::getIsOk);
    }

    /**
     * 失败的所有原因
     *
     * @param context
     * @return
     */
    default List<String> getFailReasons(T context) {
        return this.checks(context).parallelStream()
                .filter(checkRuleResult -> !checkRuleResult.getIsOk())
                .map(CheckResult::getReason)
                .collect(Collectors.toList());
    }

    /**
     * 满足所有规则的商品
     *
     * @param context
     * @return
     */
    default List<Product> getConformProduct(T context) {
        final List<CheckResult> ruleResults = this.checks(context);
        final List<Product> unConform = ruleResults.parallelStream()
                .flatMap(checkRuleResult -> checkRuleResult.getUnConform().stream())
                .collect(Collectors.toList());
        final List<Product> conform = ruleResults.parallelStream()
                .flatMap(checkRuleResult -> checkRuleResult.getConform().stream())
                .collect(Collectors.toList());
        return conform.parallelStream().filter(product -> !unConform.contains(product)).distinct().collect(Collectors.toList());
    }

    /**
     * 处理优惠
     *
     * @param context
     * @return
     */
    T handle(T context);
}
