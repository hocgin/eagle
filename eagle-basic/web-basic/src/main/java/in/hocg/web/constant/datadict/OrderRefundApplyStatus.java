package in.hocg.web.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/3/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("退款申请状态")
@RequiredArgsConstructor
public enum OrderRefundApplyStatus implements DataDictEnum {
    Pending(0, "待处理"),
    Returning(1, "退货中"),
    Completed(2, "已完成"),
    Rejected(3, "已拒绝");
    private final Integer code;
    private final String name;

    public static final String KEY = "refundApplyStatus";
}
