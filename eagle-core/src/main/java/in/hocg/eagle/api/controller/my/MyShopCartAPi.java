package in.hocg.eagle.api.controller.my;


import in.hocg.eagle.api.pojo.qo.MyCartItemPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemInsertRo;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemUpdateRo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [订单模块] 购物车表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/my/shop-cart")
public class MyShopCartAPi {
    private final AppService service;

    @UseLogger("分页查询 - 购物车")
    @PostMapping("/_paging")
    public Result pagingMyCartItem(@Validated @RequestBody MyCartItemPagingApiQo qo) {
        return Result.success(service.pagingMyCartItem(qo));
    }

    @UseLogger("加入我的购物车 - 购物车")
    @PostMapping
    public Result insertMyCartItem(@Validated @RequestBody CartItemInsertRo ro) {
        service.insertMyCartItem(ro);
        return Result.success();
    }

    @UseLogger("更新我的购物车项 - 购物车")
    @PutMapping
    public Result updateMyCartItem(@Validated @RequestBody CartItemUpdateRo ro) {
        service.updateMyCartItem(ro);
        return Result.success();
    }

    @UseLogger("删除我的购物车项 - 购物车")
    @DeleteMapping
    public Result deleteMyCartItem(@Validated @RequestBody IdRo qo) {
        service.deleteMyCartItem(qo);
        return Result.success();
    }
}

