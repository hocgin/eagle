package in.hocg.eagle.api.controller;

import in.hocg.eagle.api.service.AppService;
import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.order.CalcOrderQo;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
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
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/order")
public class OrderAPi {
    private final AppService appService;

    @UseLogger("计算订单价格")
    @PostMapping("/calc")
    public Result<CalcOrderVo> calcOrder(@Validated @RequestBody CalcOrderQo qo) {
        return Result.success(appService.calcOrder(qo));
    }
}
