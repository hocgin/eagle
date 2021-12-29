package in.hocg.eagle.api.controller.my;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.pojo.qo.MyOrderPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "eagle::我的订单")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/my/order")
public class MyOrderAPi {
    private final AppService service;

    @UseLogger("创建订单")
    @ApiOperation("创建订单")
    @PostMapping("/create")
    public Result<String> createOrder(@Validated @RequestBody CreateOrderQo qo) {
        return Result.success(service.createMyOrder(qo));
    }

    @UseLogger("申请退款")
    @ApiOperation("申请退款")
    @PostMapping("/refund")
    public Result<Void> applyRefund(@Validated @RequestBody RefundApplyQo qo) {
        service.createMyApplyRefund(qo);
        return Result.success();
    }

    @UseLogger("取消订单")
    @ApiOperation("取消订单")
    @PostMapping("/cancel")
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> cancelOrder(@Validated @RequestBody CancelOrderQo qo) {
        service.cancelMyOrder(qo);
        return Result.success();
    }

    @UseLogger("确认订单")
    @ApiOperation("确认订单")
    @PostMapping("/confirm")
    public Result<Void> confirmOrder(@Validated @RequestBody ConfirmOrderQo qo) {
        service.confirmMyOrder(qo);
        return Result.success();
    }

    @UseLogger("支付订单")
    @ApiOperation("支付订单")
    @PostMapping("/pay")
    public Result payOrder(@Validated @RequestBody PayOrderQo qo) {
        return Result.success(service.payOrder(qo));
    }

    @UseLogger("订单详情")
    @ApiOperation("订单详情")
    @GetMapping("/{id}")
    public Result<OrderComplexVo> getMyOrder(@PathVariable("id") Long id) {
        final IdRo qo = new IdRo();
        qo.setId(id);
        return Result.success(service.getMyOrder(qo));
    }

    @UseLogger("搜索个人订单")
    @ApiOperation("搜索个人订单")
    @PostMapping("/_paging")
    public Result<IPage<OrderComplexVo>> pagingMyOrder(@Validated @RequestBody MyOrderPagingApiQo qo) {
        return Result.success(service.pagingMyOrder(qo));
    }
}
