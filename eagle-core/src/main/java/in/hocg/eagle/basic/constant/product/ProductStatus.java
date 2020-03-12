package in.hocg.eagle.basic.constant.product;

import in.hocg.eagle.basic.constant.datadict.IntEnum;
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
public enum ProductStatus implements IntEnum {
    SoldOut(0, "下架"),
    Shelves(1, "上架");
    private final Integer code;
    private final String name;
}
