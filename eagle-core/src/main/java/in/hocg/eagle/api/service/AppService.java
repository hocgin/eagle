package in.hocg.eagle.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.AppMapping;
import in.hocg.eagle.api.pojo.qo.*;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.bmw.pojo.vo.GoPayVo;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemInsertRo;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemUpdateRo;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.cart.CartItemComplexVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.CartItemService;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderRefundApplyService;
import in.hocg.eagle.modules.oms.service.OrderService;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import in.hocg.eagle.modules.pms.service.ProductService;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressSaveQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;
import in.hocg.eagle.modules.ums.service.AccountAddressService;
import in.hocg.eagle.modules.ums.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    private final AccountAddressService accountAddressService;
    private final AccountService accountService;

    @Transactional(rollbackFor = Exception.class)
    public Optional<String> getAvatarUrlByUsername(String username) {
        return accountService.getAvatarUrlByUsername(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderComplexVo getMyOrder(IdRo qo) {
        return orderService.getMyOrderById(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public IPage<OrderComplexVo> pagingMyOrder(MyOrderPagingApiQo qo) {
        return orderService.pagingMyOrder(mapping.asOrderPagingQo(qo));
    }

    @Transactional(rollbackFor = Exception.class)
    public IPage<ProductComplexVo> pagingByShopping(ShoppingProductPagingApiQo qo) {
        return productService.pagingByShopping(mapping.asProductPagingQo(qo));
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductComplexVo getByShoppingAndId(Long id) {
        return productService.getByShoppingAndId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public GoPayVo payOrder(PayOrderQo qo) {
        return orderService.goPay(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createMyApplyRefund(RefundApplyQo qo) {
        orderRefundApplyService.createMyApplyRefund(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelMyOrder(CancelOrderQo qo) {
        orderService.cancelMyOrder(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirmMyOrder(ConfirmOrderQo qo) {
        orderService.confirmMyOrder(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public CalcOrderVo calcOrder(CalcOrderQo qo) {
        return orderService.calcOrder(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public String createMyOrder(CreateOrderQo qo) {
        return orderService.createMyOrder(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public IPage<CouponAccountComplexVo> pagingMyCoupon(MyCouponPagingApiQo qo) {
        return couponAccountService.pagingMyCoupon(mapping.asCouponAccountPagingQo(qo));
    }

    @Transactional(rollbackFor = Exception.class)
    public IPage<CartItemComplexVo> pagingMyCartItem(MyCartItemPagingApiQo qo) {
        return cartItemService.pagingMyCartItem(mapping.asCartItemPagingQo(qo));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMyCartItem(IdRo qo) {
        cartItemService.deleteMyCartItem(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertMyCartItem(CartItemInsertRo ro) {
        cartItemService.insertMyCartItem(ro);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMyCartItem(CartItemUpdateRo qo) {
        cartItemService.updateMyCartItem(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertMyAddress(AccountAddressSaveQo qo) {
        accountAddressService.insertMyAddress(qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMyAddress(Long id, AccountAddressSaveQo qo) {
        accountAddressService.updateMyAddress(id, qo);
    }

    @Transactional(rollbackFor = Exception.class)
    public IPage<AccountAddressComplexVo> pagingMyAddress(MyAddressPagingApiQo qo) {
        return accountAddressService.pagingMyAddress(mapping.asAccountAddressPageQo(qo));
    }

    @Transactional(rollbackFor = Exception.class)
    public AccountAddressComplexVo getMyAddress(IdRo ro) {
        return accountAddressService.getMyAddress(ro);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMyAddress(IdRo qo) {
        accountAddressService.deleteMyAddress(qo);
    }

}
