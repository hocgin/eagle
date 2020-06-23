package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.modules.bmw.entity.PaymentPlatform;
import in.hocg.eagle.modules.bmw.mapper.PaymentPlatformMapper;
import in.hocg.eagle.modules.bmw.service.PaymentPlatformService;
import in.hocg.web.AbstractServiceImpl;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.constant.datadict.PaymentWay;
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
        final List<PaymentPlatform> result = this.selectListByPlatformTypeAndEnabled(platformCode, enabled.getCode());
        return result.isEmpty()
            ? Optional.empty()
            : Optional.ofNullable(result.get(0));
    }

    @Override
    public Optional<PaymentPlatform> selectOneByPlatformAppidAndPlatformType(String platformAppid, Integer platformType) {
        return lambdaQuery().eq(PaymentPlatform::getPlatformAppid, platformAppid).eq(PaymentPlatform::getPlatformType, platformType).oneOpt();
    }

    public List<PaymentPlatform> selectListByPlatformTypeAndEnabled(Integer platformType, Integer enabled) {
        return lambdaQuery().eq(PaymentPlatform::getPlatformType, platformType).eq(PaymentPlatform::getEnabled, enabled).list();
    }
}
