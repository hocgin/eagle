package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.bmw.entity.PaymentTransaction;
import in.hocg.eagle.modules.bmw.mapper.PaymentTransactionMapper;
import in.hocg.eagle.modules.bmw.service.PaymentTransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * [支付网关] 交易流水表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-30
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentTransactionServiceImpl extends AbstractServiceImpl<PaymentTransactionMapper, PaymentTransaction> implements PaymentTransactionService {

    @Override
    public Optional<PaymentTransaction> selectOneByTransactionSn(String transactionSn) {
        return lambdaQuery().eq(PaymentTransaction::getTransactionSn, transactionSn).oneOpt();
    }

    @Override
    public boolean updateOneByIdAndTradeStatus(@NonNull PaymentTransaction update, @NonNull Long id, @NonNull Integer... tradeStatus) {
        return lambdaUpdate().eq(PaymentTransaction::getId, id)
            .in(PaymentTransaction::getTradeStatus, tradeStatus).update(update);
    }
}
