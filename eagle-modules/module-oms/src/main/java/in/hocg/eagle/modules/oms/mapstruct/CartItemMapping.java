package in.hocg.eagle.modules.oms.mapstruct;

import in.hocg.eagle.modules.oms.entity.CartItem;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemSaveQo;
import in.hocg.eagle.modules.oms.pojo.vo.cart.CartItemComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface CartItemMapping {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addProductPrice", ignore = true)
    @Mapping(target = "addProductImageUrl", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "addProductTitle", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "skuSpecData", ignore = true)
    CartItem asCartItem(CartItemSaveQo qo);

    @Mapping(target = "cartItemStatusName", ignore = true)
    @Mapping(target = "cartItemStatus", ignore = true)
    CartItemComplexVo asCartItemComplexVo(CartItem entity);
}
