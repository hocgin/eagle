package in.hocg.eagle.api;

import in.hocg.eagle.api.pojo.qo.ProductPagingApiQo;
import in.hocg.eagle.api.pojo.qo.SelfCouponPagingApiQo;
import in.hocg.eagle.api.pojo.qo.SelfOrderPagingApiQo;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import in.hocg.eagle.modules.oms.pojo.qo.order.OrderPagingQo;
import in.hocg.eagle.modules.pms.pojo.qo.ProductPagingQo;
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
    OrderPagingQo asOrderPagingQo(SelfOrderPagingApiQo qo);

    @Mapping(target = "publishStatus", ignore = true)
    ProductPagingQo asProductPagingQo(ProductPagingApiQo qo);

    @Mapping(target = "accountId", ignore = true)
    CouponAccountPagingQo asCouponAccountPagingQo(SelfCouponPagingApiQo qo);
}
