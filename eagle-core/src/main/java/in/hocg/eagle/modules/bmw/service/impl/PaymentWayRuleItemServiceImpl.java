package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.modules.bmw.entity.PaymentWayRuleItem;
import in.hocg.eagle.modules.bmw.mapper.PaymentWayRuleItemMapper;
import in.hocg.eagle.modules.bmw.service.PaymentWayRuleItemService;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [支付网关] 支付渠道规则对应的支付渠道表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-07-18
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentWayRuleItemServiceImpl extends AbstractServiceImpl<PaymentWayRuleItemMapper, PaymentWayRuleItem> implements PaymentWayRuleItemService {

}
