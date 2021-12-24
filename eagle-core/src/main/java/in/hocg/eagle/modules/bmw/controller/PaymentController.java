package in.hocg.eagle.modules.bmw.controller;

import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.bmw.api.ro.GoPayRo;
import in.hocg.eagle.modules.bmw.api.ro.QueryPaymentWayRo;
import in.hocg.eagle.modules.bmw.api.vo.PaymentWayVo;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.MessageContext;
import in.hocg.eagle.modules.bmw.pojo.vo.GoPayVo;
import in.hocg.eagle.modules.bmw.pojo.vo.WaitPaymentTradeVo;
import in.hocg.eagle.modules.bmw.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hocgin on 2020/5/31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    /**
     * 支付回调
     *
     * @param feature     支付功能: 支付、退款
     * @param platformTyp 支付平台: 微信、支付宝
     * @param appid       支付平台的唯一标识
     * @param paymentWay  支付方式
     * @param data
     * @return
     */
    @UseLogger("支付回调")
    @RequestMapping("/{feature}/{platformTyp}/{appid}/{paymentWay}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> doPayResultMessage(@PathVariable("feature") Integer feature,
                                                     @PathVariable("platformTyp") Integer platformTyp,
                                                     @PathVariable("appid") String appid,
                                                     @PathVariable("paymentWay") Integer paymentWay,
                                                     @RequestBody String data) {
        final MessageContext messageContext = new MessageContext()
            .setAppid(appid)
            .setPlatformTyp(platformTyp)
            .setFeature(feature)
            .setPaymentWay(paymentWay);
        return ResponseEntity.ok(paymentService.handleMessage(messageContext, data));
    }

    @UseLogger("发起去支付")
    @ResponseBody
    @PostMapping("/pay")
    public Result<GoPayVo> goPay(@Validated @RequestBody GoPayRo ro) {
        return Result.success(paymentService.goPay(ro));
    }

    @UseLogger("查询待支付交易单")
    @ResponseBody
    @GetMapping("/trade")
    public Result<WaitPaymentTradeVo> getWaitPaymentTrade(@RequestParam("tradeSn") String tradeSn) {
        return Result.success(paymentService.getWaitPaymentTrade(tradeSn));
    }

    @UseLogger("查询交易渠道")
    @ResponseBody
    @GetMapping("/payment-way")
    public Result<List<PaymentWayVo>> getPaymentWay(@Validated @RequestBody QueryPaymentWayRo ro) {
        return Result.success(paymentService.queryPaymentWay(ro));
    }
}
