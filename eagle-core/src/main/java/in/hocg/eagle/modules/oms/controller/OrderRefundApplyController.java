package in.hocg.eagle.modules.oms.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.oms.pojo.qo.refund.OrderRefundApplyPagingQo;
import in.hocg.eagle.modules.oms.service.OrderRefundApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

