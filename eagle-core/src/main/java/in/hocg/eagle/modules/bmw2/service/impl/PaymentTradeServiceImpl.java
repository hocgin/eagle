package in.hocg.eagle.modules.bmw2.service.impl;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.bmw2.entity.PaymentTrade;
import in.hocg.eagle.modules.bmw2.mapper.PaymentTradeMapper;
import in.hocg.eagle.modules.bmw2.pojo.vo.TradeStatusSync;
import in.hocg.eagle.modules.bmw2.service.PaymentTradeService;
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
 * @since 2020-06-06
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentTradeServiceImpl extends AbstractServiceImpl<PaymentTradeMapper, PaymentTrade> implements PaymentTradeService {

    @Override
    public Optional<PaymentTrade> selectOneByTradeSn(String tradeSn) {
        return lambdaQuery().eq(PaymentTrade::getTradeSn, tradeSn).oneOpt();
    }

    @Override
    public boolean updateOneByIdAndTradeStatus(@NonNull PaymentTrade update, @NonNull Long tradeId, @NonNull Integer... tradeStatus) {
        return lambdaUpdate().eq(PaymentTrade::getId, tradeId)
            .in(PaymentTrade::getTradeStatus, Lists.newArrayList(tradeStatus)).update(update);
    }

}
