package in.hocg.eagle.api;

import in.hocg.eagle.api.pojo.qo.*;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemPagingQo;
import in.hocg.eagle.modules.oms.pojo.qo.order.OrderPagingQo;
import in.hocg.eagle.modules.pms.pojo.qo.ProductPagingQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressPageQo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface AppMapping {
    @Mapping(target = "accountId", ignore = true)
    OrderPagingQo asOrderPagingQo(MyOrderPagingApiQo qo);

    @Mapping(target = "publishStatus", ignore = true)
    ProductPagingQo asProductPagingQo(ShoppingProductPagingApiQo qo);

    @Mapping(target = "couponId", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    CouponAccountPagingQo asCouponAccountPagingQo(MyCouponPagingApiQo qo);

    @Mapping(target = "accountId", ignore = true)
    CartItemPagingQo asCartItemPagingQo(MyCartItemPagingApiQo qo);

    @Mapping(target = "accountId", ignore = true)
    AccountAddressPageQo asAccountAddressPageQo(MyAddressPagingApiQo qo);
}
