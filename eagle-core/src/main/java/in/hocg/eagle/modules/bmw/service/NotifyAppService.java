package in.hocg.eagle.modules.bmw.service;

import in.hocg.eagle.modules.bmw.entity.NotifyApp;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;

/**
 * <p>
 * [支付网关] 事件通知列表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
public interface NotifyAppService extends AbstractService<NotifyApp> {

    Long savePaymentNotify(Long tradeId);

    Long saveRefundNotify(Long refundId);
}
