package in.hocg.eagle.modules.oms.helper.order2.rule;

import in.hocg.eagle.modules.oms.helper.order2.discount.Discount;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2020/7/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class DiscountCheckResult {
    @Getter
    private final List<CheckResult> results;
    @Getter
    private final Discount discount;

    public DiscountCheckResult(Discount discount, List<CheckResult> results) {
        this.discount = discount;
        this.results = results;
    }

    public boolean isOk() {
        return results.parallelStream().allMatch(CheckResult::getIsOk);
    }

    public List<String> getErrorMessage() {
        return results.parallelStream().map(CheckResult::getReason).collect(Collectors.toList());
    }

    public Optional<String> getFirstErrorMessage() {
        return results.parallelStream().filter(checkResult -> !checkResult.getIsOk())
            .map(CheckResult::getReason)
            .filter(Objects::nonNull).findFirst();
    }

}
