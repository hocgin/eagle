package in.hocg.eagle.modules.bmw.controller;

import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.bmw.pojo.ro.GoPayRo;
import in.hocg.eagle.modules.bmw.pojo.vo.GoPayVo;
import in.hocg.eagle.modules.bmw.service.PaymentService;
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
     *
     * @param feature 支付功能: 微信JSAPI、支付宝
     * @param channel 支付平台: 微信、支付宝
     * @param appid   支付平台的唯一标识
     * @param data
     * @return
     */
    @UseLogger("支付回调")
    @RequestMapping("/{feature}/{channel}/{appid}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> doPayResultMessage(@PathVariable("feature") Integer feature,
                                                     @PathVariable("channel") Integer channel,
                                                     @PathVariable("appid") String appid,
                                                     @RequestBody String data) {
        return ResponseEntity.ok(paymentService.handleMessage(feature, channel, appid, data));
    }

    @UseLogger("生成支付信息")
    @ResponseBody
    @PostMapping("/pay")
    @Transactional(rollbackFor = Exception.class)
    public Result<GoPayVo> goPay(@Validated @RequestBody GoPayRo ro) {
        return Result.success(paymentService.goPay(ro));
    }
}
