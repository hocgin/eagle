package in.hocg.eagle.modules.oms.service.impl;

import com.alibaba.fastjson.JSON;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.KeyValue;
import in.hocg.eagle.modules.oms.mapstruct.OrderItemMapping;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.entity.OrderRefundApply;
import in.hocg.eagle.modules.oms.mapper.OrderItemMapper;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderItemComplexVo;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderRefundApplyService;
import in.hocg.eagle.utils.ValidUtils;
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
    private final OrderRefundApplyService orderRefundApplyService;

    @Override
    public List<OrderItemComplexVo> selectListByOrderId(Long orderId) {
        return selectListByOrderId2(orderId).stream()
            .map(this::convertComplex)
            .collect(Collectors.toList());
    }

    @Override
    public OrderItemComplexVo selectOne(Long id) {
        final OrderItem entity = getById(id);
        ValidUtils.notNull(entity);
        return convertComplex(entity);
    }

    @Override
    public List<OrderItem> selectListByOrderId2(Long orderId) {
        return lambdaQuery().eq(OrderItem::getOrderId, orderId).list();
    }

    private OrderItemComplexVo convertComplex(OrderItem entity) {
        final OrderItemComplexVo result = mapping.asOrderItemComplexVo(entity);
        final Long id = entity.getId();
        final Optional<OrderRefundApply> orderRefundApply = orderRefundApplyService.selectOneByOrderItemId(id);
        if (orderRefundApply.isPresent()) {
            final OrderRefundApply apply = orderRefundApply.get();
            result.setRefundApplySn(apply.getApplySn());
            result.setRefundApplyId(apply.getId());
            result.setRefundStatus(apply.getApplyStatus());
        }
        result.setSpec(JSON.parseArray(entity.getProductSpecData(), KeyValue.class));
        return result;
    }
}
