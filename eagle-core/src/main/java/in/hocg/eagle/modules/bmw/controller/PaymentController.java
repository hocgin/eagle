package in.hocg.eagle.modules.bmw.controller;

import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.bmw.helper.payment.PaymentService;
import in.hocg.eagle.modules.bmw.helper.payment.request.PaymentRequestResult;
import in.hocg.eagle.modules.bmw.pojo.qo.GoPayQo;
import in.hocg.eagle.modules.bmw.pojo.qo.PaymentQo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 支付回调
     * @param channel 支付渠道: 微信、支付宝
     * @param feature 支付功能: 微信JSAPI、支付宝
     * @param data
     * @return
     */
    @UseLogger("支付回调")
    @RequestMapping("/{channel}/{feature}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> doPayResultMessage(@PathVariable("channel") Integer channel,
                                                     @PathVariable("feature") Integer feature,
                                                     @RequestBody String data) {
        return ResponseEntity.ok(paymentService.handleMessage(channel, feature, data));
    }

    @UseLogger("生成支付信息")
    @ResponseBody
    @PostMapping("/pay")
    @Transactional(rollbackFor = Exception.class)
    public Result<PaymentRequestResult> goPay(@Validated @RequestBody PaymentQo qo) {
        return Result.success(paymentService.goPay(GoPayQo.builder()
            .transactionSn(qo.getTransactionSn())
            .paymentWay(qo.getPaymentWay())
            .build()));
    }
}
