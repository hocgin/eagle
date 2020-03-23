package in.hocg.eagle.modules.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.pojo.qo.order.OrderPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    IPage<Order> paging(@Param("qo") OrderPagingQo qo, @Param("page") Page page);

}
