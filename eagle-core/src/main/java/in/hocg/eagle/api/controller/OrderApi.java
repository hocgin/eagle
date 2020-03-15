package in.hocg.eagle.api.controller;

import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.constant.AuthorizeConstant;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.order.CalcOrderQo;
import in.hocg.eagle.modules.oms.pojo.qo.order.CreateOrderQo;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@PreAuthorize(AuthorizeConstant.IS_MINI_EAGLE)
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/order")
public class OrderApi {
    private final OrderService orderService;

    @UseLogger("计算订单价格")
    @PostMapping("/calc")
    private Result<CalcOrderVo> calcOrder(@Validated @RequestBody CalcOrderQo qo) {
        return Result.success(orderService.calcOrder(qo));
    }

    @UseLogger("创建订单")
    @PostMapping("/calc")
    private Result<Void> createOrder(@Validated @RequestBody CreateOrderQo qo) {
        orderService.createOrder(qo);
        return Result.success();
    }
}
