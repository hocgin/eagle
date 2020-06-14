package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.invoke.MethodHandles;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("订单状态")
@RequiredArgsConstructor
public enum OrderStatus implements DataDictEnum {
    PendingPayment(0, "待付款"),
    ToBeDelivered(1, "待发货"),
    Shipped(2, "已发货"),
    Completed(3, "已完成"),
    Closed(4, "已关闭"),
    InvalidOrder(5, "无效订单");
    private final Integer code;
    private final String name;

    public static final String KEY = "orderStatus";
}
