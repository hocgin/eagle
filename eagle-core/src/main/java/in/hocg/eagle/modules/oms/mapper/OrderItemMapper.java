package in.hocg.eagle.modules.oms.mapper;

import in.hocg.eagle.modules.oms.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * [订单模块] 订单商品表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    Integer updateAdjustmentDiscountAmountIf(@Param("updated") OrderItem updated, @Param("ifAdjustmentDiscountAmount") BigDecimal ifAdjustmentDiscountAmount);
}
