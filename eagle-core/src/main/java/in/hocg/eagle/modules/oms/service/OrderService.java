package in.hocg.eagle.modules.oms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.bmw.pojo.vo.GoPayVo;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.AvailableDiscountVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("取消订单 - 我的订单")
    @Transactional(rollbackFor = Exception.class)
    void cancelMyOrder(CancelOrderQo qo);

    @ApiOperation("订单详情 - 我的订单")
    @Transactional(rollbackFor = Exception.class)
    OrderComplexVo getMyOrderById(IdRo qo);

    @ApiOperation("确认收货 - 我的订单")
    @Transactional(rollbackFor = Exception.class)
    void confirmMyOrder(ConfirmOrderQo qo);

    @ApiOperation("分页查询 - 我的订单")
    @Transactional(rollbackFor = Exception.class)
    IPage<OrderComplexVo> pagingMyOrder(OrderPagingQo qo);

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
    String createMyOrder(CreateOrderQo qo);

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
     * 确认发货
     *
     * @param qo
     */
    void shippedOrder(ShippedOrderQo qo);

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


    /**
     * 支付成功回调处理
     * @param qo
     */
    void asyncOrderMessage(AsyncOrderMessageQo qo);

    /**
     * 删除订单
     *
     * @param qo
     */
    void deleteOne(IdRo qo);

    /**
     * 更新订单
     * - 后台优惠
     * - 收件人信息
     *
     * @param qo
     */
    void updateOne(UpdateOrderQo qo);

    /**
     * 生成支付信息
     *
     * @param qo
     * @return
     */
    GoPayVo goPay(PayOrderQo qo);

    AvailableDiscountVo listAvailableCouponsByOrder(CalcOrderQo qo);

    void log(Order newOrder);
}
