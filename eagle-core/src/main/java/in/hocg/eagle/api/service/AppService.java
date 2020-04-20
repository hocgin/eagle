package in.hocg.eagle.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.AppMapping;
import in.hocg.eagle.api.pojo.qo.*;
import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.constant.datadict.OrderPayType;
import in.hocg.eagle.basic.constant.datadict.OrderStatus;
import in.hocg.eagle.basic.constant.datadict.ProductPublishStatus;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.manager.payment.PaymentManager;
import in.hocg.eagle.manager.payment.dto.PayOrderDto;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemPagingQo;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemSaveQo;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.cart.CartItemComplexVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.CartItemService;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderRefundApplyService;
import in.hocg.eagle.modules.oms.service.OrderService;
import in.hocg.eagle.modules.pms.pojo.qo.ProductPagingQo;
import in.hocg.eagle.modules.pms.pojo.vo.product.ProductComplexVo;
import in.hocg.eagle.modules.pms.service.ProductService;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressPageQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressSaveQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;
import in.hocg.eagle.modules.ums.service.AccountAddressService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.web.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Supplier;

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
    private final CartItemService cartItemService;
    private final CouponAccountService couponAccountService;
    private final OrderItemService orderItemService;
    private final AppMapping mapping;
    private final ProductService productService;
    private final PaymentManager paymentManager;
    private final AccountAddressService accountAddressService;

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
        final Optional<HttpServletRequest> request = SpringContext.getRequest();
        String clientIp = "127.0.0.1";
        if (request.isPresent()) {
            clientIp = RequestUtils.getClientIP(request.get());
        }
        final Long id = qo.getId();
        final Optional<OrderPayType> payTypeOpl = IntEnum.of(qo.getPayType(), OrderPayType.class);
        final OrderPayType payType = payTypeOpl.orElseThrow((Supplier<Throwable>) () -> ServiceException.wrap("错误的支付方式"));

        final OrderComplexVo orderComplex = orderService.selectOne(id);
        if (!LangUtils.equals(OrderStatus.PendingPayment.getCode(), orderComplex.getOrderStatus())) {
            throw ServiceException.wrap("操作失败，请检查订单的支付状态");
        }
        return paymentManager.payOrder(new PayOrderDto()
            .setOrder(orderComplex)
            .setPayType(payType)
            .setClientIp(clientIp));
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

    @Transactional(rollbackFor = Exception.class)
    public IPage<CartItemComplexVo> pagingSelfCartItem(SelfCartItemPagingApiQo qo) {
        CartItemPagingQo newQo = mapping.asCartItemPagingQo(qo);
        newQo.setAccountId(qo.getUserId());
        return cartItemService.paging(newQo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOneWithCartItem(CartItemSaveQo qo) {
        cartItemService.saveOne(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteOneWithCartItem(IdQo qo) {
        cartItemService.deleteOne(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOneWithAccountAddress(AccountAddressSaveQo qo) {
        accountAddressService.saveOne(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public IPage<AccountAddressComplexVo> pagingWithAccountAddress(SelfAccountAddressPagingApiQo qo) {
        final AccountAddressPageQo newQo = new AccountAddressPageQo();
        newQo.setAccountId(qo.getUserId());
        return accountAddressService.paging(newQo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteOneWithAccountAddress(IdQo qo) {
        accountAddressService.deleteOne(qo);
    }
}
