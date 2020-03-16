package in.hocg.eagle.modules.oms.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
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

    @Transactional(rollbackFor = Exception.class)
    void payOrder(PayOrderQo qo);

    @Transactional(rollbackFor = Exception.class)
    void confirmOrder(ConfirmOrderQo qo);

    @Transactional(rollbackFor = Exception.class)
    void applyRefund(RefundApplyQo qo);
}
