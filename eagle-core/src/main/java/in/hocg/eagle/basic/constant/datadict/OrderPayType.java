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
public enum OrderPayType implements DataDictEnum {
    Unknown(0, "未支付"),
    AliPay(1, "支付宝"),
    WxPay(2, "微信");
    private final Integer code;
    private final String name;

    public static final String KEY = "orderPayType";
}
