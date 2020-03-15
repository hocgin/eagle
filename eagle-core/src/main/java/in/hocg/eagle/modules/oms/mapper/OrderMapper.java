package in.hocg.eagle.modules.oms.mapper;

import in.hocg.eagle.modules.oms.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [订单模块] 订单主表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
