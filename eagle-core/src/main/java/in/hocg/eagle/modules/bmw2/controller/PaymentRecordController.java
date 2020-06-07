package in.hocg.eagle.modules.bmw2.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [支付网关] 支付记录表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/bmw2/payment-record")
public class PaymentRecordController {

}

