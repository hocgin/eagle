package in.hocg.eagle.basic.lang;

import in.hocg.eagle.basic.SnowFlake;
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
    public String getOrderRefundApplySNCode() {
        return getSNCode(Type.OrderRefundApply);
    }

    /**
     * 订单编号
     * TODO :: 生成18位订单编号:8日期 + 2位平台 + 2位支付方式 + 6位自增ID
     *
     * @return
     */
    public String getOrderSNCode() {
        return getSNCode(Type.Order);
    }

    public String getCouponAccountSNCode() {
        return getSNCode(Type.CouponAccount);

    }

    @Getter
    @RequiredArgsConstructor
    enum Type {
        Order("E1"),
        OrderRefundApply("R1"),
        CouponAccount("C1");
        private final String code;
    }

    private String getSNCode(Type type) {
        final long code = snowFlake.nextId();
        return type.getCode() + code;
    }

}
