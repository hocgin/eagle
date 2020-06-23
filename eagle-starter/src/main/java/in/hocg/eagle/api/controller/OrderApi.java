package in.hocg.eagle.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.pojo.qo.SelfOrderPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.constant.AuthorizeConstant;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.web.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@PreAuthorize(AuthorizeConstant.IS_MINI_EAGLE)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/order")
public class OrderApi {
    private final AppService appService;

    @UseLogger("计算订单价格")
    @PostMapping("/calc")
    public Result<CalcOrderVo> calcOrder(@Validated @RequestBody CalcOrderQo qo) {
        return Result.success(appService.calcOrder(qo));
    }

    @UseLogger("创建订单")
    @PostMapping("/create")
    public Result<Void> createOrder(@Validated @RequestBody CreateOrderQo qo) {
        appService.createOrder(qo);
        return Result.success();
    }

    @UseLogger("申请退款")
    @PostMapping("/refund")
    public Result<Void> applyRefund(@Validated @RequestBody RefundApplyQo qo) {
        appService.applyRefund(qo);
        return Result.success();
    }

    @UseLogger("取消订单")
    @PostMapping("/cancel")
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> cancelOrder(@Validated @RequestBody CancelOrderQo qo) {
        appService.cancelOrder(qo);
        return Result.success();
    }

    @UseLogger("确认订单")
    @PostMapping("/confirm")
    public Result<Void> confirmOrder(@Validated @RequestBody ConfirmOrderQo qo) {
        appService.confirmOrder(qo);
        return Result.success();
    }

    @UseLogger("支付订单")
    @PostMapping("/pay")
    public Result payOrder(@Validated @RequestBody PayOrderQo qo) throws Throwable {
        return Result.success(appService.payOrder(qo));
    }

    @UseLogger("订单详情")
    @GetMapping("/{id}")
    public Result<OrderComplexVo> selectOne(@PathVariable("id") Long id) {
        final IdQo qo = new IdQo();
        qo.setId(id);
        return Result.success(appService.getSelfOrderById(qo));
    }

    @UseLogger("搜索个人订单")
    @PostMapping("/_paging")
    public Result<IPage<OrderComplexVo>> paging(@Validated @RequestBody SelfOrderPagingApiQo qo) {
        return Result.success(appService.pagingSelfOrder(qo));
    }
}
