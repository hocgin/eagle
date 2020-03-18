package in.hocg.eagle.modules.oms.helper;

import in.hocg.eagle.modules.oms.helper.modal.AbsOrder;
import in.hocg.eagle.basic.constant.datadict.OrderSourceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by hocgin on 2019-10-14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GeneralOrder extends AbsOrder<GeneralProduct> {

    @ApiModelProperty("账户ID")
    private final Integer accountId;
    @ApiModelProperty("订单来源")
    private final OrderSourceType sourceType;
    @ApiModelProperty("创建时间")
    private final LocalDateTime createAt;

    public GeneralOrder(List<GeneralProduct> products, @NonNull Integer accountId, @NonNull OrderSourceType sourceType, @NonNull LocalDateTime createAt) {
        super(products);
        this.accountId = accountId;
        this.sourceType = sourceType;
        this.createAt = createAt;
    }

    public String toStrings() {
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("==== 订单信息 ====")
            // 订单信息
            // 金额信息
            .add("订单优惠前的金额: " + this.getPreDiscountTotalAmount())
            .add("订单优惠后的金额: " + this.getPreferentialAmount())
            .add("订单优惠抵扣金额: -" + this.getDiscountTotalAmount());

        // 订单内的商品列表
        stringJoiner.add("==== 商品信息 ====");
        this.getProducts().forEach(item -> {
            stringJoiner.add(item.toStrings());
        });

        // 订单使用的优惠券信息
        stringJoiner.add("==== 订单使用的优惠券信息 ====");
        this.getUseDiscount().forEach(item -> {
            stringJoiner.add(item.toStrings(2));
        });
        return stringJoiner.toString();
    }
}
