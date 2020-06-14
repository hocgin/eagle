package in.hocg.web.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@RequiredArgsConstructor
public enum FeatureType {
    Refund(0, "退款"),
    Payment(1, "支付");
    private final Integer code;
    private final String name;
}
