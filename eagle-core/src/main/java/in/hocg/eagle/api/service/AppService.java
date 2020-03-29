package in.hocg.eagle.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.AppMapping;
import in.hocg.eagle.api.pojo.qo.ProductPagingApiQo;
import in.hocg.eagle.api.pojo.qo.SelfCouponPagingApiQo;
import in.hocg.eagle.api.pojo.qo.SelfOrderPagingApiQo;
import in.hocg.eagle.api.pojo.qo.SignUpApiQo;
import in.hocg.eagle.basic.constant.datadict.ProductPublishStatus;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.manager.PaymentManager;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderRefundApplyService;
import in.hocg.eagle.modules.oms.service.OrderService;
import in.hocg.eagle.modules.pms.pojo.qo.ProductPagingQo;
import in.hocg.eagle.modules.pms.pojo.vo.product.ProductComplexVo;
import in.hocg.eagle.modules.pms.service.ProductService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AppService {
    private final OrderService orderService;
    private final OrderRefundApplyService orderRefundApplyService;
    private final CouponAccountService couponAccountService;
    private final OrderItemService orderItemService;
    private final AppMapping mapping;
    private final ProductService productService;
    private final PaymentManager paymentManager;

    public void signUp(SignUpApiQo qo) {

    }

    @Transactional(rollbackFor = Exception.class)
    public OrderComplexVo getSelfOrderById(IdQo qo) {
        final Long orderId = qo.getId();
        final Long userId = qo.getUserId();
        final OrderComplexVo result = orderService.selectOne(orderId);
        ValidUtils.isTrue(LangUtils.equals(result.getAccountId(), userId), "非订单拥有人");
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public IPage<OrderComplexVo> pagingSelfOrder(SelfOrderPagingApiQo qo) {
        OrderPagingQo newQo = mapping.asOrderPagingQo(qo);
        newQo.setAccountId(qo.getUserId());
        return orderService.paging(newQo);
    }

    @Transactional(rollbackFor = Exception.class)
    public IPage<ProductComplexVo> pagingProduct(ProductPagingApiQo qo) {
        ProductPagingQo newQo = mapping.asProductPagingQo(qo);
        newQo.setPublishStatus(ProductPublishStatus.Shelves.getCode());
        return productService.paging(newQo);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductComplexVo getProductById(IdQo qo) {
        final ProductComplexVo result = productService.selectOne(qo.getId());
        ValidUtils.isTrue(LangUtils.equals(result.getPublishStatus(), ProductPublishStatus.Shelves.getCode()), "商品已下架");
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public String payOrder(PayOrderQo qo) throws Throwable {
        return paymentManager.payOrder(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public String doPayResultMessage(Integer channel, Integer feature, String data) {
        return paymentManager.doPayResultMessage(channel, feature, data);
    }

    @Transactional(rollbackFor = Exception.class)
    public void applyRefund(RefundApplyQo qo) {
        final Long orderItemId = qo.getOrderItemId();
        final OrderItem orderItem = orderItemService.getById(orderItemId);
        ValidUtils.notNull(orderItem);
        final Long orderId = orderItem.getOrderId();
        final Order order = orderService.getById(orderId);
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), qo.getUserId()), "非订单所有人，操作失败");
        orderRefundApplyService.applyRefund(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(CancelOrderQo qo) {
        final Order order = orderService.getById(qo.getId());
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), qo.getUserId()), "非订单所有人，操作失败");
        orderService.cancelOrder(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirmOrder(ConfirmOrderQo qo) {
        final Order order = orderService.getById(qo.getId());
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), qo.getUserId()), "非订单所有人，操作失败");
        orderService.confirmOrder(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public CalcOrderVo calcOrder(CalcOrderQo qo) {
        return orderService.calcOrder(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(CreateOrderQo qo) {
        orderService.createOrder(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public IPage<CouponAccountComplexVo> pagingSelfCoupon(SelfCouponPagingApiQo qo) {
        CouponAccountPagingQo newQo = mapping.asCouponAccountPagingQo(qo);
        newQo.setAccountId(qo.getUserId());
        return couponAccountService.paging(newQo);
    }
}
