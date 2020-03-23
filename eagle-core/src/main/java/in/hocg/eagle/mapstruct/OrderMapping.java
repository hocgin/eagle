package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.pojo.dto.order.OrderItemDto;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface OrderMapping {


    @Mapping(target = "couponId", ignore = true)
    @Mapping(target = "orderStatusName", ignore = true)
    @Mapping(target = "sourceTypeName", ignore = true)
    @Mapping(target = "payTypeName", ignore = true)
    @Mapping(target = "confirmStatusName", ignore = true)
    @Mapping(target = "accountName", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    OrderComplexVo asOrderComplexVo(Order entity);

    @Mapping(target = "spec", ignore = true)
    @Mapping(target = "title", source = "dto.productName")
    @Mapping(target = "specData", source = "dto.productSpecData")
    @Mapping(target = "skuId", source = "dto.productSkuId")
    @Mapping(target = "skuCode", source = "dto.productSkuCode")
    @Mapping(target = "quantity", source = "dto.productQuantity")
    @Mapping(target = "price", source = "dto.productPrice")
    @Mapping(target = "imageUrl", source = "dto.productPic")
    CalcOrderVo.OrderItem asCalcOrderVoOrderItem(OrderItemDto dto);
}
