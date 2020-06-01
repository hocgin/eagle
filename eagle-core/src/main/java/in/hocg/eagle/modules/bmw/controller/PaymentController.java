package in.hocg.eagle.modules.bmw.controller;

import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.modules.bmw.helper.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hocgin on 2020/5/31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @UseLogger("支付回调")
    @RequestMapping("/{channel}/{feature}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> doPayResultMessage(@PathVariable("channel") Integer channel,
                                                     @PathVariable("feature") Integer feature,
                                                     @RequestBody String data) {
        return ResponseEntity.ok(paymentService.handleMessage(channel, feature, data));
    }
}
