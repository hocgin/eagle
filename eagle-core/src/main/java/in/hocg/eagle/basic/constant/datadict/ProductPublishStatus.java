package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("商品状态")
@RequiredArgsConstructor
public enum ProductPublishStatus implements IntEnum {
    SoldOut(0, "下架"),
    Shelves(1, "上架");
    private final Integer code;
    private final String name;

    public static final String KEY = "publishStatus";
}
