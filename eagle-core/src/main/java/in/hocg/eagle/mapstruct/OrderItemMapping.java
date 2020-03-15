package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderItemComplexVo;
import org.mapstruct.Mapper;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapping {


    OrderItemComplexVo asOrderItemComplexVo(OrderItem entity);
}
