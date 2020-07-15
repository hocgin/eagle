package in.hocg.eagle.modules.oms.helper.order2.modal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GeneralProduct extends AbsProduct {
    private final Integer productQuantity;
    private Long productId;
    private Long productSkuId;
    private Long productCategoryId;
    private BigDecimal productPrice;

    public GeneralProduct(BigDecimal productPrice, Integer productQuantity) {
        super(productPrice.multiply(new BigDecimal(productQuantity)));
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }
}
