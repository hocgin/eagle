package in.hocg.eagle.modules.oms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.oms.entity.CartItem;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemPagingQo;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemSaveQo;
import in.hocg.eagle.modules.oms.pojo.vo.cart.CartItemComplexVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;

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

    @ApiOperation("删除我的购物车项 - 购物车")
    @Transactional(rollbackFor = Exception.class)
    void deleteMyCartItem(IdRo qo);

    @ApiOperation("查询我的购物车 - 购物车")
    @Transactional(rollbackFor = Exception.class)
    IPage<CartItemComplexVo> pagingMyCartItem(CartItemPagingQo qo);

    IPage<CartItemComplexVo> paging(CartItemPagingQo qo);

    void insertMyCartItem(CartItemSaveQo qo);

    void updateMyCartItem(CartItemSaveQo qo);
}
