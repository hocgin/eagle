package in.hocg.eagle.modules.oms.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.SNCode;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.constant.datadict.OrderRefundApplyStatus;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.mapstruct.OrderRefundApplyMapping;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.entity.OrderRefundApply;
import in.hocg.eagle.modules.oms.mapper.OrderRefundApplyMapper;
import in.hocg.eagle.modules.oms.pojo.qo.order.RefundApplyQo;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderRefundApplyService;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class OrderRefundApplyServiceImpl extends AbstractServiceImpl<OrderRefundApplyMapper, OrderRefundApply>
    implements OrderRefundApplyService {
    private final OrderItemService orderItemService;
    private final OrderRefundApplyService orderRefundApplyService;
    private final OrderRefundApplyMapping orderRefundApplyMapping;
    private final SNCode snCode;

    @Override
    public Optional<OrderRefundApply> selectOneByOrderItemId(Long orderItemId) {
        return lambdaQuery().eq(OrderRefundApply::getOrderItemId, orderItemId).oneOpt();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyRefund(RefundApplyQo qo) {
        final Long orderItemId = qo.getOrderItemId();
        final OrderItem orderItem = orderItemService.getById(orderItemId);
        ValidUtils.notNull(orderItem, "订单商品不存在");
        final Optional<OrderRefundApply> orderRefundApplyOpt = orderRefundApplyService.selectOneByOrderItemId(orderItemId);
        if (orderRefundApplyOpt.isPresent()) {
            final OrderRefundApply apply = orderRefundApplyOpt.get();
            final OrderRefundApplyStatus applyStatus = IntEnum.ofThrow(apply.getApplyStatus(), OrderRefundApplyStatus.class);
            throw ServiceException.wrap("已进行退款申请，申请状态为" + applyStatus.getName());
        }

        OrderRefundApply apply = orderRefundApplyMapping.asOrderRefundApply(qo, orderItem);
        apply.setRefundQuantity(orderItem.getProductQuantity());
        apply.setApplySn(snCode.getOrderRefundApplySNCode());
        apply.setApplyStatus(OrderRefundApplyStatus.Pending.getCode());
        apply.setCreatedAt(qo.getCreatedAt());
        apply.setCreator(qo.getUserId());
        orderRefundApplyService.validInsert(apply);
    }
}
