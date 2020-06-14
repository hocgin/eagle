package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("购物车商品状态")
@RequiredArgsConstructor
public enum CartItemStatus implements DataDictEnum {
    Normal(0, "正常"),
    Expired(1, "已过期"),
    UnderStock(2, "库存不足");
    private final Integer code;
    private final String name;

    public static final String KEY = "cartItemStatus";
}
