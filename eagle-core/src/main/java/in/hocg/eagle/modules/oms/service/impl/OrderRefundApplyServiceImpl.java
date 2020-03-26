package in.hocg.eagle.modules.oms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.SNCode;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.constant.datadict.OrderRefundApplyStatus;
import in.hocg.eagle.basic.constant.datadict.OrderStatus;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.mapstruct.OrderRefundApplyMapping;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.entity.OrderRefundApply;
import in.hocg.eagle.modules.oms.mapper.OrderRefundApplyMapper;
import in.hocg.eagle.modules.oms.pojo.qo.order.RefundApplyQo;
import in.hocg.eagle.modules.oms.pojo.qo.refund.OrderRefundApplyPagingQo;
import in.hocg.eagle.modules.oms.pojo.vo.refund.OrderRefundApplyComplexVo;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderRefundApplyService;
import in.hocg.eagle.modules.oms.service.OrderService;
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
    private final OrderService orderService;
    private final OrderRefundApplyMapping mapping;
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
        final Long orderId = orderItem.getOrderId();
        final Order order = orderService.getById(orderId);
        if (!Lists.newArrayList(OrderStatus.Shipped.getCode(),
            OrderStatus.Completed.getCode()).contains(order.getOrderStatus())) {
            throw ServiceException.wrap("订单当前状态无法进行退款操作");
        }

        final Optional<OrderRefundApply> orderRefundApplyOpt = this.selectOneByOrderItemId(orderItemId);
        if (orderRefundApplyOpt.isPresent()) {
            final OrderRefundApply apply = orderRefundApplyOpt.get();
            final OrderRefundApplyStatus applyStatus = IntEnum.ofThrow(apply.getApplyStatus(), OrderRefundApplyStatus.class);
            throw ServiceException.wrap("已进行退款申请，申请状态为" + applyStatus.getName());
        }

        OrderRefundApply apply = mapping.asOrderRefundApply(qo, orderItem);
        apply.setRefundQuantity(orderItem.getProductQuantity());
        apply.setApplySn(snCode.getOrderRefundApplySNCode());
        apply.setApplyStatus(OrderRefundApplyStatus.Pending.getCode());
        apply.setCreatedAt(qo.getCreatedAt());
        apply.setCreator(qo.getUserId());
        this.validInsert(apply);
    }

    @Override
    public IPage<OrderRefundApplyComplexVo> paging(OrderRefundApplyPagingQo qo) {
        return baseMapper.paging(qo, qo.page()).convert(this::convertOrderRefundApplyComplex);
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderRefundApplyComplexVo selectOne(Long id) {
        final OrderRefundApply entity = getById(id);
        ValidUtils.notNull(entity, "未找对退费申请单");
        return convertOrderRefundApplyComplex(entity);
    }

    public OrderRefundApplyComplexVo convertOrderRefundApplyComplex(OrderRefundApply entity) {
        return mapping.asOrderRefundApplyComplex(entity);
    }
}
