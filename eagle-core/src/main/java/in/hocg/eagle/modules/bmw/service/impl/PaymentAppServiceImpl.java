package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.modules.bmw.entity.PaymentApp;
import in.hocg.eagle.modules.bmw.mapper.PaymentAppMapper;
import in.hocg.eagle.modules.bmw.service.PaymentAppService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * <p>
 * [支付网关] 接入方表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentAppServiceImpl extends AbstractServiceImpl<PaymentAppMapper, PaymentApp> implements PaymentAppService {

    @Override
    public Optional<PaymentApp> selectOneByAppSn(Long appSn) {
        return lambdaQuery().eq(PaymentApp::getAppSn, appSn).oneOpt();
    }
}
