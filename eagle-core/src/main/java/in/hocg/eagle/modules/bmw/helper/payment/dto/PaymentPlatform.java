package in.hocg.eagle.modules.bmw.helper.payment.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/6/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("支付平台")
@RequiredArgsConstructor
public enum PaymentPlatform {
    AliPay(0, "支付宝"),
    WxPay(1, "微信支付");
    private final Integer code;
    private final String name;
}
