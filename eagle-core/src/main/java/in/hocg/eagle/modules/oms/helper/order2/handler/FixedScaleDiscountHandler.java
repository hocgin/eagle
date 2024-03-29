package in.hocg.eagle.modules.oms.helper.order2.handler;


import in.hocg.eagle.modules.oms.helper.order2.GLog;
import in.hocg.eagle.modules.oms.helper.order2.discount.AbsFixedScaleDiscount;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order2.modal.GeneralProduct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * Created by hocgin on 2020/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class FixedScaleDiscountHandler extends DefaultDiscountHandler<AbsFixedScaleDiscount, GeneralOrder, GeneralProduct> {

    @Override
    public void handle(AbsFixedScaleDiscount discount, GeneralOrder context, List<GeneralProduct> usedProduct) {
        if (usedProduct.isEmpty()) {
            return;
        }

        // 获取匹配的商品优惠前的价格
        BigDecimal discountTotalAmount = context.getProductTotalRealAmount(usedProduct);
        if (BigDecimal.ZERO.compareTo(discountTotalAmount) >= 0) {
            return;
        }
        final BigDecimal scale = discount.getScale();
        final BigDecimal discountLimit = discount.getDiscountLimit();

        // 计算出 订单按比例折扣 进行优惠的金额
        // 例如: 100 进行 85折优惠 => 25
        BigDecimal val = discountTotalAmount.subtract(discountTotalAmount.multiply(scale).setScale(2, RoundingMode.DOWN));

        // 判定限制金额
        if (Objects.nonNull(discountLimit) && val.compareTo(discountLimit) > 0) {
            val = discountLimit;
        }

        BigDecimal cval = val;

        GLog.addLog(String.format("=================👇%s👇=============== ", "本次需要优惠金额：" + val));
        BigDecimal useTotalDiscountPrice = BigDecimal.ZERO;
        for (int i = 0; i < usedProduct.size(); i++) {
            GeneralProduct product = usedProduct.get(i);
            BigDecimal preDiscountPrice = product.getTotalAmount();
            BigDecimal preferentialPrice = product.getRealAmount();
            BigDecimal discountPrice = product.getDiscountAmount();

            // 该商品预计扣减优惠金额
            BigDecimal useDiscountPrice;
            if (i < usedProduct.size() - 1) {
                // 优惠比例
                useDiscountPrice = val.multiply(preferentialPrice).divide(discountTotalAmount, 0, RoundingMode.DOWN);


                cval = cval.subtract(useDiscountPrice);

                GLog.addLog("\n折扣前金额：" + preferentialPrice
                        + "\n折扣比例：" + scale
                        + "\n商品折扣金额：" + preDiscountPrice.subtract(useDiscountPrice)
                        + "\n商品折扣金额(向下取整后)：" + useDiscountPrice
                );
            } else {
                useDiscountPrice = cval;
                useDiscountPrice = useDiscountPrice.setScale(0, RoundingMode.DOWN);

                GLog.addLog("\n折扣前金额：" + preferentialPrice
                        + "\n商品折扣金额：" + cval
                        + "\n折扣前金额(向下取整)：" + useDiscountPrice
                );
            }

            // 本次折扣后商品金额为负数，重新修正本次优惠的金额
            if (useDiscountPrice.add(discountPrice).compareTo(preDiscountPrice) > 0) {
                useDiscountPrice = preDiscountPrice.subtract(discountPrice);
            }

            useTotalDiscountPrice = useTotalDiscountPrice.add(useDiscountPrice);

            GLog.addLog(String.format("统计: 优惠金额=%s", useDiscountPrice));
            product.addUseDiscount(discount, useDiscountPrice);
        }
    }
}
