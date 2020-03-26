package in.hocg.eagle.modules.oms.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

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
@RequestMapping("/oms/order-refund-apply")
public class OrderRefundApplyController {

}

