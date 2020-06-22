package in.hocg.eagle.modules.oms.service;

import in.hocg.web.AbstractService;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderItemComplexVo;

import java.util.List;

/**
 * <p>
 * [订单模块] 订单商品表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
public interface OrderItemService extends AbstractService<OrderItem> {

    /**
     * 获取订单的商品项
     *
     * @param orderId
     * @return
     */
    List<OrderItemComplexVo> selectListByOrderId(Long orderId);

    OrderItemComplexVo selectOne(Long id);

    List<OrderItem> selectListByOrderId2(Long orderId);
}
