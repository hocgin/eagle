package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.modules.bmw.api.vo.PaymentWayVo;
import in.hocg.eagle.modules.bmw.datastruct.PaymentWayRuleItemMapping;
import in.hocg.eagle.modules.bmw.entity.PaymentWayRule;
import in.hocg.eagle.modules.bmw.entity.PaymentWayRuleItem;
import in.hocg.eagle.modules.bmw.mapper.PaymentWayRuleMapper;
import in.hocg.eagle.modules.bmw.service.PaymentWayRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * [支付网关] 支付渠道规则表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-07-18
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentWayRuleServiceImpl extends AbstractServiceImpl<PaymentWayRuleMapper, PaymentWayRule>
    implements PaymentWayRuleService {
    private final PaymentWayRuleItemMapping mapping;

    @Override
    @Transactional
    public List<PaymentWayVo> queryPaymentWay(Long appId, String sceneCode) {
        final List<PaymentWayRuleItem> result = baseMapper.selectListByAppIdAndSceneCode(appId, sceneCode);
        return mapping.asPaymentWayVo(result);
    }
}
