package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.NotifyEvent;
import in.hocg.eagle.basic.constant.datadict.NotifyEventStatus;
import in.hocg.eagle.modules.bmw.entity.NotifyAppLog;
import in.hocg.eagle.modules.bmw.mapper.NotifyAppLogMapper;
import in.hocg.eagle.modules.bmw.service.NotifyAppLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>
 * [支付网关] 所有通知应用方日志表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-01
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class NotifyAppLogServiceImpl extends AbstractServiceImpl<NotifyAppLogMapper, NotifyAppLog>
    implements NotifyAppLogService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getOrCreate(NotifyEvent notifyEvent, Integer paymentWay, String tradeNo, String orderSn, BigDecimal totalFee) {
        final Optional<NotifyAppLog> notifyAppLogOpt = selectOneByTradeNoAndNotifyEvent(tradeNo, notifyEvent);

        final NotifyAppLog entity = new NotifyAppLog()
            .setTotalFee(totalFee)
            .setOrderSn(orderSn)
            .setTradeNo(tradeNo)
            .setPaymentWay(paymentWay)
            .setNotifyEvent(notifyEvent.getCode())
            .setNotifyEventStatus(NotifyEventStatus.Init.getCode())
            .setCreatedAt(LocalDateTime.now());
        this.validInsert(entity);


    }

    public Optional<NotifyAppLog> selectOneByTradeNoAndNotifyEvent(String tradeNo, NotifyEvent notifyEvent) {
        return lambdaQuery().eq(NotifyAppLog::getTradeNo, tradeNo)
            .eq(NotifyAppLog::getNotifyEvent, notifyEvent.getCode()).oneOpt();
    }
}
