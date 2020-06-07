package in.hocg.eagle.modules.bmw2.service;

import in.hocg.eagle.modules.bmw2.entity.PaymentApp;
import in.hocg.eagle.basic.AbstractService;

import java.util.Optional;

/**
 * <p>
 * [支付网关] 接入方表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
public interface PaymentAppService extends AbstractService<PaymentApp> {

    Optional<PaymentApp> selectOneByAppSn(Long appSn);
}
