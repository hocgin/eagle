package in.hocg.eagle.modules.oms.controller;


import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.refund.HandleQo;
import in.hocg.eagle.modules.oms.pojo.qo.refund.OrderRefundApplyPagingQo;
import in.hocg.eagle.modules.oms.service.OrderRefundApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单退货申请 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-16
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/order-refund-apply")
public class OrderRefundApplyController {
    private final OrderRefundApplyService service;

    @UseLogger("分页查询 - 订单退费申请列表")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody OrderRefundApplyPagingQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("获取 - 订单退费申请详情")
    @GetMapping("/{id}")
    public Result selectOne(@PathVariable Long id) {
        return Result.success(service.selectOne(id));
    }

    @UseLogger("处理 - 退费申请")
    @PostMapping("/{id}/handle")
    public Result handle(@PathVariable Long id,
                         @Validated @RequestBody HandleQo qo) {
        qo.setId(id);
        service.handle(qo);
        return Result.success();
    }

}

