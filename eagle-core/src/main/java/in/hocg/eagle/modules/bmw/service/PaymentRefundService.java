package in.hocg.eagle.modules.bmw.service;

import in.hocg.eagle.modules.bmw.entity.PaymentRefund;
import in.hocg.eagle.basic.AbstractService;
import lombok.NonNull;

import java.util.Optional;

/**
 * <p>
 * [支付网关] 退款记录表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-01
 */
public interface PaymentRefundService extends AbstractService<PaymentRefund> {

    Optional<PaymentRefund> selectOneByRefundSn(String refundSn);

    boolean updateOneByIdAndTradeStatus(@NonNull PaymentRefund update, @NonNull Long id, @NonNull Integer refundStatus);
}
