package in.hocg.eagle.basic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SNCode {
    private final SnowFlake snowFlake;

    /**
     * 订单退款申请编号
     *
     * @return
     */
    public String getOrderReturnApplySNCode() {
        return getSNCode(Type.OrderReturnApply);
    }

    /**
     * 订单编号
     *
     * @return
     */
    public String getOrderSNCode() {
        return getSNCode(Type.Order);
    }

    @Getter
    @RequiredArgsConstructor
    enum Type {
        Order("E1"),
        OrderReturnApply("R1");
        private final String code;
    }

    private String getSNCode(Type type) {
        final long code = snowFlake.nextId();
        return type.getCode() + code;
    }

}
