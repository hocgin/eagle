package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.entity.OrderRefundApply;
import in.hocg.eagle.modules.oms.pojo.qo.order.RefundApplyQo;
import in.hocg.eagle.modules.oms.pojo.vo.refund.OrderRefundApplyComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/3/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface OrderRefundApplyMapping {


    @Mapping(target = "refundAmount", ignore = true)
    @Mapping(target = "refundQuantity", ignore = true)
    @Mapping(target = "applySn", ignore = true)
    @Mapping(target = "refundRemark", source = "qo.refundRemark")
    @Mapping(target = "refundReason", source = "qo.refundReason")
    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "receiveRemark", ignore = true)
    @Mapping(target = "receiveAt", ignore = true)
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
    OrderRefundApply asOrderRefundApply(RefundApplyQo qo, OrderItem orderItem);

    @Mapping(target = "creatorName", ignore = true)
    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "receiverName", ignore = true)
    @Mapping(target = "handlerName", ignore = true)
    @Mapping(target = "applyStatusName", ignore = true)
    OrderRefundApplyComplexVo asOrderRefundApplyComplex(OrderRefundApply entity);
}
