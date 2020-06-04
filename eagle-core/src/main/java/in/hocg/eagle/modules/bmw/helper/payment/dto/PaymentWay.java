package in.hocg.eagle.modules.bmw.helper.payment.dto;

import in.hocg.eagle.basic.constant.datadict.IntEnum;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/5/31.
 * email: hocgin@gmail.com
 *
 * @uthor hocgin
 */
@Getter
@ApiModel("支付方式")
@RequiredArgsConstructor
public enum PaymentWay implements IntEnum {
    // 支付宝
    AliPayWithApp(PaymentPlatform.AliPay, 1, "支付宝 - APP"),
    AliPayWithWap(PaymentPlatform.AliPay, 2, "支付宝 - Wap/Native"),
    AliPayWithPC(PaymentPlatform.AliPay, 3, "支付宝 - PC"),
    AliPayWithQrCode(PaymentPlatform.AliPay, 4, "支付宝 - QrCode"),
    //微信
    WxPayWithJSAPI(PaymentPlatform.WxPay, 5, "微信 - JSAPI"),
    WxPayWithApp(PaymentPlatform.WxPay, 6, "微信 - APP"),
    WxPayWithNative(PaymentPlatform.WxPay, 7, "微信 - Native");
    private final PaymentPlatform platform;
    private final Integer code;
    private final String name;

    public String getNotifyUrl() {
        return "/" + platform.getCode() + "/" + getCode();
    }
}
