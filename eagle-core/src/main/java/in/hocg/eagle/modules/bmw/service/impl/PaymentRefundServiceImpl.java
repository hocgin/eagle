package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.bmw.entity.PaymentRefund;
import in.hocg.eagle.modules.bmw.mapper.PaymentRefundMapper;
import in.hocg.eagle.modules.bmw.service.PaymentRefundService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * [支付网关] 退款记录表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-01
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentRefundServiceImpl extends AbstractServiceImpl<PaymentRefundMapper, PaymentRefund> implements PaymentRefundService {

    @Override
    public Optional<PaymentRefund> selectOneByRefundSn(String refundSn) {
        return lambdaQuery().eq(PaymentRefund::getRefundSn, refundSn).oneOpt();
    }

    @Override
    public boolean updateOneByIdAndTradeStatus(@NonNull PaymentRefund update, @NonNull Long id, @NonNull Integer refundStatus) {
        return lambdaUpdate().eq(PaymentRefund::getId, id)
            .eq(PaymentRefund::getRefundStatus, refundStatus).update(update);
    }
}
