package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("支付类型")
@RequiredArgsConstructor
public enum OrderPayType implements IntEnum {
    AliPay(0, "支付宝"),
    WxPay(1, "微信");
    private final Integer code;
    private final String name;

    public static final String KEY = "orderPayType";
}
