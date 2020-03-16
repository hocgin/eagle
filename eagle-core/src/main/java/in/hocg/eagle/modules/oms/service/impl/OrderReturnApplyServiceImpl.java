package in.hocg.eagle.modules.oms.service.impl;

import in.hocg.eagle.modules.oms.entity.OrderReturnApply;
import in.hocg.eagle.modules.oms.mapper.OrderReturnApplyMapper;
import in.hocg.eagle.modules.oms.service.OrderReturnApplyService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * <p>
 * 订单退货申请 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-16
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderReturnApplyServiceImpl extends AbstractServiceImpl<OrderReturnApplyMapper, OrderReturnApply> implements OrderReturnApplyService {

    @Override
    public Optional<OrderReturnApply> selectOneByOrderItemId(Long orderItemId) {
        return lambdaQuery().eq(OrderReturnApply::getOrderItemId, orderItemId).oneOpt();
    }
}
