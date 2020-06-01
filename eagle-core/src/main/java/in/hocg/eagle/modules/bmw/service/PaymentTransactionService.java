package in.hocg.eagle.modules.bmw.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.bmw.entity.PaymentTransaction;

import java.util.Optional;

/**
 * <p>
 * [支付网关] 交易流水表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-30
 */
public interface PaymentTransactionService extends AbstractService<PaymentTransaction> {

    Optional<PaymentTransaction> selectOneByTransactionSn(String transactionSn);

    boolean updateOneByIdAndTradeStatus(PaymentTransaction update, Long id, Integer... tradeStatus);
}
