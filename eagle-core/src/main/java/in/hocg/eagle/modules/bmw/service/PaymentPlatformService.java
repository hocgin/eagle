package in.hocg.eagle.modules.bmw.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.datadict.PaymentWay;
import in.hocg.eagle.modules.bmw.entity.PaymentPlatform;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * [支付网关] 支付平台表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
public interface PaymentPlatformService extends AbstractService<PaymentPlatform> {

    Optional<PaymentPlatform> selectOneByTradeIdAndPaymentWayAndStatus(Long tradeId, PaymentWay paymentWay, Enabled enabled);

}
