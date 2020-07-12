package in.hocg.eagle.modules.oms.mapstruct;

import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderItemComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapping {


    @Mapping(target = "discountTotalAmount", ignore = true)
    @Mapping(target = "refundApplySn", ignore = true)
    @Mapping(target = "refundApplyId", ignore = true)
    @Mapping(target = "spec", ignore = true)
    @Mapping(target = "refundStatus", ignore = true)
    @Mapping(target = "refundStatusName", ignore = true)
    OrderItemComplexVo asOrderItemComplexVo(OrderItem entity);
}
