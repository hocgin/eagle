package in.hocg.eagle.modules.oms.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.OrderItemMapping;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.mapper.OrderItemMapper;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderItemComplexVo;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * [订单模块] 订单商品表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderItemServiceImpl extends AbstractServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
    private final OrderItemMapping mapping;

    @Override
    public List<OrderItemComplexVo> selectListByOrderId(Long orderId) {
        return selectListByOrderId2(orderId).stream().map(this::convertOrderItemComplex).collect(Collectors.toList());
    }

    private OrderItemComplexVo convertOrderItemComplex(OrderItem entity) {
        return mapping.asOrderItemComplexVo(entity);
    }

    private List<OrderItem> selectListByOrderId2(Long orderId) {
        return lambdaQuery().eq(OrderItem::getOrderId, orderId).list();
    }
}
