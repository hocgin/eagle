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
    AliPay(0, "支付宝"),
    WxPay(1, "微信支付");
    private final Integer code;
    private final String name;
}
