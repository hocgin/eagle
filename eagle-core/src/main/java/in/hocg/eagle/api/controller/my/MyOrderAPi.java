package in.hocg.eagle.api.controller.my;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.pojo.qo.MyOrderPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hocgin on 2020/7/3.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/my/order")
public class MyOrderAPi {
    private final AppService service;


    @UseLogger("根据订单获取优惠券信息 - 个人优惠券")
    @PostMapping("/coupon")
    public Result getAvailableCouponsByOrder(@Validated @RequestBody CalcOrderQo qo) {
        return Result.success(service.listAvailableCouponsByOrder(qo));
    }

    @UseLogger("创建订单")
    @PostMapping("/create")
    public Result<String> createOrder(@Validated @RequestBody CreateOrderQo qo) {
        return Result.success(service.createMyOrder(qo));
    }

    @UseLogger("申请退款")
    @PostMapping("/refund")
    public Result<Void> applyRefund(@Validated @RequestBody RefundApplyQo qo) {
        service.createMyApplyRefund(qo);
        return Result.success();
    }

    @UseLogger("取消订单")
    @PostMapping("/cancel")
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> cancelOrder(@Validated @RequestBody CancelOrderQo qo) {
        service.cancelMyOrder(qo);
        return Result.success();
    }

    @UseLogger("确认订单")
    @PostMapping("/confirm")
    public Result<Void> confirmOrder(@Validated @RequestBody ConfirmOrderQo qo) {
        service.confirmMyOrder(qo);
        return Result.success();
    }

    @UseLogger("支付订单")
    @PostMapping("/pay")
    public Result payOrder(@Validated @RequestBody PayOrderQo qo) {
        return Result.success(service.payOrder(qo));
    }

    @UseLogger("订单详情")
    @GetMapping("/{id}")
    public Result<OrderComplexVo> getMyOrder(@PathVariable("id") Long id) {
        final IdRo qo = new IdRo();
        qo.setId(id);
        return Result.success(service.getMyOrder(qo));
    }

    @UseLogger("搜索个人订单")
    @PostMapping("/_paging")
    public Result<IPage<OrderComplexVo>> pagingMyOrder(@Validated @RequestBody MyOrderPagingApiQo qo) {
        return Result.success(service.pagingMyOrder(qo));
    }
}
