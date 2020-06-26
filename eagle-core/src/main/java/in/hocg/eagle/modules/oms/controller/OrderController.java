package in.hocg.eagle.modules.oms.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [订单模块] 订单主表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService service;

    @UseLogger("分页查询 - 订单列表")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody OrderPagingQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("获取 - 订单详情")
    @GetMapping("/{id}")
    public Result selectOne(@PathVariable Long id) {
        return Result.success(service.selectOne(id));
    }

    @UseLogger("关闭 - 订单")
    @PutMapping("/close")
    public Result close(@Validated @RequestBody CancelOrderQo qo) {
        service.cancelOrder(qo);
        return Result.success();
    }

    @UseLogger("发货 - 订单")
    @PutMapping("/shipped")
    public Result shipped(@Validated @RequestBody ShippedOrderQo qo) {
        service.shippedOrder(qo);
        return Result.success();
    }

    @UseLogger("修改 - 订单")
    @PutMapping("/{id}")
    public Result updateOne(@PathVariable Long id,
                            @Validated @RequestBody UpdateOrderQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }

    @UseLogger("删除 - 订单")
    @DeleteMapping("/{id}")
    public Result deleteOne(@PathVariable Long id) {
        final IdRo qo = new IdRo();
        qo.setId(id);
        service.deleteOne(qo);
        return Result.success();
    }

    @UseLogger("通知订单状态 - 订单")
    @PostMapping("/async")
    public String asyncOrderMessage(@RequestBody AsyncOrderMessageQo qo) {
        service.asyncOrderMessage(qo);
        return "SUCCESS";
    }
}

