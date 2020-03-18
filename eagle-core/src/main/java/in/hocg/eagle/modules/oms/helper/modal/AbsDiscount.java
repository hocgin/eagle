package in.hocg.eagle.modules.oms.helper.modal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2019-10-14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public abstract class AbsDiscount<T extends Order, P extends Product>
    implements Discount<T, P> {

    /**
     * 包含的商品
     */
    private List<P> usedProduct = new ArrayList<>();

    /**
     * 该折扣进行优惠的金额
     */
    private BigDecimal totalDiscountPrice = BigDecimal.ZERO;

    @Override
    public List<P> getUsedProduct() {
        return this.usedProduct;
    }

    @Override
    public BigDecimal getDiscountTotalAmount() {
        return totalDiscountPrice;
    }

    @Override
    public void handle(T order, List<P> usedProduct) {
        if (CollectionUtils.isEmpty(usedProduct)) {
            return;
        }
        this.usedProduct = Collections.unmodifiableList(usedProduct);
        this.totalDiscountPrice = _handle(order, this.usedProduct);
    }

}
