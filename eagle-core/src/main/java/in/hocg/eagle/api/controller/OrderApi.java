package in.hocg.eagle.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.pojo.SelfOrderPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
//@PreAuthorize(AuthorizeConstant.IS_MINI_EAGLE)
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/order")
public class OrderApi {
    private final OrderService orderService;
    private final AppService appService;

    @UseLogger("计算订单价格")
    @PostMapping("/calc")
    private Result<CalcOrderVo> calcOrder(@Validated @RequestBody CalcOrderQo qo) {
        return Result.success(orderService.calcOrder(qo));
    }

    @UseLogger("创建订单")
    @PostMapping("/create")
    private Result<Void> createOrder(@Validated @RequestBody CreateOrderQo qo) {
        orderService.createOrder(qo);
        return Result.success();
    }

    @UseLogger("申请退款")
    @PostMapping("/refund")
    private Result<Void> applyRefund(@Validated @RequestBody RefundApplyQo qo) {
        orderService.applyRefund(qo);
        return Result.success();
    }

    @UseLogger("取消订单")
    @PostMapping("/cancel")
    private Result<Void> cancelOrder(@Validated @RequestBody CancelOrderQo qo) {
        orderService.cancelOrder(qo);
        return Result.success();
    }

    @UseLogger("确认订单")
    @PostMapping("/confirm")
    private Result<Void> confirmOrder(@Validated @RequestBody ConfirmOrderQo qo) {
        orderService.confirmOrder(qo);
        return Result.success();
    }

    @UseLogger("支付订单")
    @PostMapping("/pay")
    private Result<Void> payOrder(@Validated @RequestBody PayOrderQo qo) {
        orderService.payOrder(qo);
        return Result.success();
    }

    @UseLogger("订单详情")
    @GetMapping("/{id}")
    private Result<OrderComplexVo> selectOne(@PathVariable("id") Long id) {
        final IdQo qo = new IdQo();
        qo.setId(id);
        return Result.success(appService.getSelfOrderById(qo));
    }

    @UseLogger("搜索个人订单")
    @PostMapping("/_paging")
    private Result<IPage<OrderComplexVo>> paging(@Validated @RequestBody SelfOrderPagingApiQo qo) {
        return Result.success(appService.pagingSelfOrder(qo));
    }
}
