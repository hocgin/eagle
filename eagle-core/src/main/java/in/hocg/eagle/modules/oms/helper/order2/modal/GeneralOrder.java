package in.hocg.eagle.modules.oms.helper.order2.modal;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeneralOrder extends AbsOrder<GeneralProduct> {

    private final Long accountId;
    private final Integer platform;
    private final LocalDateTime createAt;

    public GeneralOrder(List<GeneralProduct> products, Long accountId, Integer platform, LocalDateTime createAt) {
        super(products);
        this.accountId = accountId;
        this.platform = platform;
        this.createAt = createAt;
    }
}
