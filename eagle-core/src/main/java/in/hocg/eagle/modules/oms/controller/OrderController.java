package in.hocg.eagle.modules.oms.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.order.OrderPagingQo;
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
    private final OrderService orderService;

    @UseLogger("分页查询订单列表")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody OrderPagingQo qo) {
        return Result.success(orderService.paging(qo));
    }

    @UseLogger("获取订单详情")
    @GetMapping("/{id}")
    public Result selectOne(@PathVariable Long id) {
        return Result.success(orderService.selectOne(id));
    }

    @UseLogger("删除订单")
    @DeleteMapping("/{id}")
    public Result deleteOne(@PathVariable Long id) {
        final IdQo qo = new IdQo();
        qo.setId(id);
        orderService.deleteOne(qo);
        return Result.success();
    }
}

