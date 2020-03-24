package in.hocg.eagle.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.pojo.SelfOrderPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.constant.AuthorizeConstant;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.OrderService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
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
    private final OrderService orderService;
    private final AppService appService;

    @UseLogger("计算订单价格")
    @PostMapping("/calc")
    @Transactional(rollbackFor = Exception.class)
    public Result<CalcOrderVo> calcOrder(@Validated @RequestBody CalcOrderQo qo) {
        return Result.success(orderService.calcOrder(qo));
    }

    @UseLogger("创建订单")
    @PostMapping("/create")
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> createOrder(@Validated @RequestBody CreateOrderQo qo) {
        orderService.createOrder(qo);
        return Result.success();
    }

    @UseLogger("支付回调")
    @RequestMapping("/{channel}/{feature}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> doPayResultMessage(@PathVariable("channel") Integer channel,
                                                     @PathVariable("feature") Integer feature,
                                                     @RequestBody String data) {
        return ResponseEntity.ok(appService.doPayResultMessage(channel, feature, data));
    }

    @UseLogger("申请退款")
    @PostMapping("/refund")
    @Transactional(rollbackFor = Exception.class)
    private Result<Void> applyRefund(@Validated @RequestBody RefundApplyQo qo) {
        final Order order = orderService.getById(qo.getId());
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), qo.getUserId()), "非订单所有人，操作失败");
        orderService.applyRefund(qo);
        return Result.success();
    }

    @UseLogger("取消订单")
    @PostMapping("/cancel")
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> cancelOrder(@Validated @RequestBody CancelOrderQo qo) {
        final Order order = orderService.getById(qo.getId());
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), qo.getUserId()), "非订单所有人，操作失败");
        orderService.cancelOrder(qo);
        return Result.success();
    }

    @UseLogger("确认订单")
    @PostMapping("/confirm")
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> confirmOrder(@Validated @RequestBody ConfirmOrderQo qo) {
        final Order order = orderService.getById(qo.getId());
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), qo.getUserId()), "非订单所有人，操作失败");
        orderService.confirmOrder(qo);
        return Result.success();
    }

    @UseLogger("支付订单")
    @PostMapping("/pay")
    @Transactional(rollbackFor = Exception.class)
    public Result<String> payOrder(@Validated @RequestBody PayOrderQo qo) throws Throwable {
        return Result.success(appService.payOrder(qo));
    }

    @UseLogger("订单详情")
    @GetMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result<OrderComplexVo> selectOne(@PathVariable("id") Long id) {
        final IdQo qo = new IdQo();
        qo.setId(id);
        return Result.success(appService.getSelfOrderById(qo));
    }

    @UseLogger("搜索个人订单")
    @PostMapping("/_paging")
    @Transactional(rollbackFor = Exception.class)
    public Result<IPage<OrderComplexVo>> paging(@Validated @RequestBody SelfOrderPagingApiQo qo) {
        return Result.success(appService.pagingSelfOrder(qo));
    }
}
