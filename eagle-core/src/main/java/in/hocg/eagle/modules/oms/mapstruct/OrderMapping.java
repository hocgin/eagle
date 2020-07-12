package in.hocg.eagle.modules.oms.mapstruct;

import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.pojo.qo.order.UpdateOrderQo;
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


    @Mapping(target = "discountTotalAmount", ignore = true)
    @Mapping(target = "couponId", ignore = true)
    @Mapping(target = "orderStatusName", ignore = true)
    @Mapping(target = "sourceTypeName", ignore = true)
    @Mapping(target = "payTypeName", ignore = true)
    @Mapping(target = "confirmStatusName", ignore = true)
    @Mapping(target = "accountName", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    OrderComplexVo asOrderComplexVo(Order entity);

    @Mapping(target = "discountAmount", ignore = true)
    @Mapping(target = "couponAccountId", ignore = true)
    @Mapping(target = "couponDiscountAmount", ignore = true)
    @Mapping(target = "adjustmentDiscountAmount", ignore = true)
    @Mapping(target = "tradeSn", ignore = true)
    @Mapping(target = "lastUpdater", source = "")
    @Mapping(target = "orderStatus", source = "")
    @Mapping(target = "orderSn", ignore = true)
    @Mapping(target = "receiveTime", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "sourceType", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "paymentTime", ignore = true)
    @Mapping(target = "payAmount", ignore = true)
    @Mapping(target = "payType", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "freightAmount", ignore = true)
    @Mapping(target = "deliveryTime", ignore = true)
    @Mapping(target = "deleteStatus", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "confirmStatus", ignore = true)
    @Mapping(target = "commentTime", ignore = true)
    @Mapping(target = "autoConfirmDay", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    Order asOrder(UpdateOrderQo qo);

}
