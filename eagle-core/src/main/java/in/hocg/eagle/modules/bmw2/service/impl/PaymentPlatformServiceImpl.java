package in.hocg.eagle.modules.bmw2.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.modules.bmw2._constant.PaymentWay;
import in.hocg.eagle.modules.bmw2.entity.PaymentPlatform;
import in.hocg.eagle.modules.bmw2.mapper.PaymentPlatformMapper;
import in.hocg.eagle.modules.bmw2.service.PaymentPlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * [支付网关] 支付平台表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentPlatformServiceImpl extends AbstractServiceImpl<PaymentPlatformMapper, PaymentPlatform> implements PaymentPlatformService {

    @Override
    public Optional<PaymentPlatform> selectOneByTradeIdAndPaymentWayAndStatus(Long tradeId, PaymentWay paymentWay, Enabled enabled) {
        final Integer platformCode = paymentWay.getPlatform().getCode();
        final List<PaymentPlatform> result = selectListByPlatformTypeAndEnabled(platformCode, enabled.getCode());
        return result.isEmpty() ? Optional.empty() : Optional.ofNullable(result.get(0));
    }

    public List<PaymentPlatform> selectListByPlatformTypeAndEnabled(Integer platformType, Integer enabled) {
        return lambdaQuery().eq(PaymentPlatform::getPlatformType, platformType).eq(PaymentPlatform::getEnabled, enabled).list();
    }
}
