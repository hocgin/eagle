package in.hocg.eagle.modules.oms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.modules.oms.entity.CartItem;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemPagingQo;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemSaveQo;
import in.hocg.eagle.modules.oms.pojo.vo.cart.CartItemComplexVo;

/**
 * <p>
 * [订单模块] 购物车表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
public interface CartItemService extends AbstractService<CartItem> {

    void saveOne(CartItemSaveQo qo);

    void deleteOne(IdQo qo);

    IPage<CartItemComplexVo> paging(CartItemPagingQo qo);
}
