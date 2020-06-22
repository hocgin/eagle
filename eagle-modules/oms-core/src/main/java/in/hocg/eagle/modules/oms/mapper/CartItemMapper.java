package in.hocg.eagle.modules.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.oms.entity.CartItem;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [订单模块] 购物车表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {

    IPage<CartItem> paging(@Param("qo") CartItemPagingQo qo, @Param("page") Page page);
}
