package in.hocg.eagle.modules.bmw.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.constant.datadict.NotifyEvent;
import in.hocg.eagle.modules.bmw.entity.NotifyAppLog;

import java.math.BigDecimal;

/**
 * <p>
 * [支付网关] 所有通知应用方日志表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-01
 */
public interface NotifyAppLogService extends AbstractService<NotifyAppLog> {

    void getOrCreate(NotifyEvent notifyEvent, Integer paymentWay, String tradeNo, String orderSn, BigDecimal totalFee);
}
