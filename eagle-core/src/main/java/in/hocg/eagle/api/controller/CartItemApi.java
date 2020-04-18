package in.hocg.eagle.api.controller;


import in.hocg.eagle.api.pojo.qo.SelfCartItemPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemSaveQo;
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
@RequestMapping("/api-mini/cart-item")
public class CartItemApi {
    private final AppService service;

    @UseLogger("分页查询 - 购物车")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody SelfCartItemPagingApiQo qo) {
        return Result.success(service.pagingSelfCartItem(qo));
    }

    @UseLogger("加入 - 购物车")
    @PostMapping
    public Result saveOne(@Validated @RequestBody CartItemSaveQo qo) {
        service.saveOneWithCartItem(qo);
        return Result.success();
    }

    @UseLogger("删除 - 购物车")
    @DeleteMapping
    public Result deleteOne(@Validated @RequestBody IdQo qo) {
        service.deleteOneWithCartItem(qo);
        return Result.success();
    }
}

