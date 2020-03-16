package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.entity.OrderReturnApply;
import in.hocg.eagle.modules.oms.pojo.qo.order.RefundApplyQo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/3/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface OrderReturnApplyMapping {


    @Mapping(target = "returnRemark", source = "qo.returnRemark")
    @Mapping(target = "returnQuantity", source = "qo.returnQuantity")
    @Mapping(target = "returnAmount", source = "qo.returnAmount")
    @Mapping(target = "returnReason", source = "qo.returnReason")
    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "receiveRemark", ignore = true)
    @Mapping(target = "productSpecData", source = "orderItem.productSpecData")
    @Mapping(target = "receiveAt", ignore = true)
    @Mapping(target = "productRealAmount", source = "orderItem.realAmount")
    @Mapping(target = "productPrice", source = "orderItem.productPrice")
    @Mapping(target = "productPic", source = "orderItem.productPic")
    @Mapping(target = "productName", source = "orderItem.productName")
    @Mapping(target = "orderItemId", source = "orderItem.id")
    @Mapping(target = "handler", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "handleRemark", ignore = true)
    @Mapping(target = "handleAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "companyAddressId", ignore = true)
    @Mapping(target = "applyStatus", ignore = true)
    OrderReturnApply asOrderReturnApply(RefundApplyQo qo, OrderItem orderItem);
}
