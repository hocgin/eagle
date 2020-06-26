package in.hocg.eagle.basic.ext.lang;

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

    /**
     * 优惠券编号
     *
     * @return
     */
    public String getCouponAccountSNCode() {
        return getSNCode(Type.CouponAccount);

    }

    /**
     * 业务日志编码
     *
     * @return
     */
    public String getBusinessSNCode() {
        return getSNCode(Type.BusinessLog);
    }

    /**
     * 支付交易流水号
     *
     * @return
     */
    public String getTransactionSNCode() {
        return getSNCode(Type.Transaction);
    }

    /**
     * 退款交易流水号
     *
     * @return
     */
    public String getRefundSNCode() {
        return getSNCode(Type.Refund);
    }

    @Getter
    @RequiredArgsConstructor
    enum Type {
        Order("E1"),
        OrderRefundApply("R1"),
        CouponAccount("C1"),
        BusinessLog("B1"),
        Transaction("T1"),
        Refund("RD1");
        private final String code;
    }

    private String getSNCode(Type type) {
        final long code = snowFlake.nextId();
        return type.getCode() + code;
    }

}
