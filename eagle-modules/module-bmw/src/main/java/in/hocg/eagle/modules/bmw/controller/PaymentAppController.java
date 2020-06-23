package in.hocg.eagle.modules.bmw.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [支付网关] 接入方表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/bmw2/payment-app")
public class PaymentAppController {

}

