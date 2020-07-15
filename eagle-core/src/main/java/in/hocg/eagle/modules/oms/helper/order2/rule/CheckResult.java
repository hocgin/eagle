package in.hocg.eagle.modules.oms.helper.order2.rule;

import in.hocg.eagle.modules.oms.helper.order2.modal.Product;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class CheckResult {
    /**
     * 是否可用
     */
    private final Boolean isOk;
    /**
     * 不可用原因
     */
    private String reason;
    /**
     * 符合
     */
    private List<? extends Product> conform = Collections.emptyList();
    /**
     * 不符合
     */
    private List<? extends Product> unConform = Collections.emptyList();

    private Rule rule;

    protected CheckResult(boolean isOk) {
        this.isOk = isOk;
    }

}
