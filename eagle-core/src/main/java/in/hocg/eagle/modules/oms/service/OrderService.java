package in.hocg.eagle.modules.oms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * [订单模块] 订单主表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
public interface OrderService extends AbstractService<Order> {

    /**
     * 计算订单
     *
     * @param qo
     * @return
     */
    CalcOrderVo calcOrder(CalcOrderQo qo);

    /**
     * 下单
     *
     * @param qo
     */
    void createOrder(CreateOrderQo qo);

    /**
     * 取消订单
     *
     * @param qo
     */
    void cancelOrder(CancelOrderQo qo);

    void payOrder(PayOrderQo qo);

    void confirmOrder(ConfirmOrderQo qo);

    void applyRefund(RefundApplyQo qo);

    OrderComplexVo selectOne(Long id);

    IPage<OrderComplexVo> paging(OrderPagingQo qo);
}
