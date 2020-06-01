package in.hocg.eagle.modules.bmw.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [支付网关] 退款记录表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-06-01
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/bmw/payment-refund")
public class PaymentRefundController {

}

