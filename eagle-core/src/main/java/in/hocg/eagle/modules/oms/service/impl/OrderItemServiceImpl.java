package in.hocg.eagle.modules.oms.service.impl;

import com.alibaba.fastjson.JSON;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.KeyValue;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.mapstruct.OrderItemMapping;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.entity.OrderReturnApply;
import in.hocg.eagle.modules.oms.mapper.OrderItemMapper;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderItemComplexVo;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderReturnApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    private final OrderReturnApplyService orderReturnApplyService;

    @Override
    public List<OrderItemComplexVo> selectListByOrderId(Long orderId) {
        return selectListByOrderId2(orderId).stream().map(this::convertOrderItemComplex).collect(Collectors.toList());
    }

    private OrderItemComplexVo convertOrderItemComplex(OrderItem entity) {
        final OrderItemComplexVo result = mapping.asOrderItemComplexVo(entity);
        final Long id = entity.getId();
        final Optional<OrderReturnApply> orderReturnApply = orderReturnApplyService.selectOneByOrderItemId(id);
        if (orderReturnApply.isPresent()) {
            final OrderReturnApply apply = orderReturnApply.get();
            result.setReturnStatus(apply.getApplyStatus());
        }
        result.setSpec(JSON.parseArray(entity.getProductSpecData(), KeyValue.class));
        return result;
    }

    @Override
    public List<OrderItem> selectListByOrderId2(Long orderId) {
        return lambdaQuery().eq(OrderItem::getOrderId, orderId).list();
    }
}
