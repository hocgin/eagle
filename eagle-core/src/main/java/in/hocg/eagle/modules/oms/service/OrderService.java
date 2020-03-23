package in.hocg.eagle.modules.oms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;

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

    /**
     * 确认收货
     *
     * @param qo
     */
    void confirmOrder(ConfirmOrderQo qo);

    /**
     * 申请退款
     *
     * @param qo
     */
    void applyRefund(RefundApplyQo qo);

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    OrderComplexVo selectOne(Long id);

    /**
     * 分页查询
     *
     * @param qo
     * @return
     */
    IPage<OrderComplexVo> paging(OrderPagingQo qo);


    void paySuccess(Integer payType, String outTradeNo);
}
