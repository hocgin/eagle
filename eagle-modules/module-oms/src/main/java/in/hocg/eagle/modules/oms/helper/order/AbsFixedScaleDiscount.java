package in.hocg.eagle.modules.oms.helper.order;

import in.hocg.eagle.modules.oms.helper.order.modal.AbsDiscount;
import in.hocg.eagle.modules.oms.helper.order.modal.AbsProduct;
import in.hocg.web.utils.LangUtils;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Created by hocgin on 2019/10/15.
 * email: hocgin@gmail.com
 * 折扣类型 - 固定比例
 *
 * @author hocgin
 */
@Getter
public abstract class AbsFixedScaleDiscount extends AbsDiscount<GeneralOrder, GeneralProduct> {

    /**
     * 折扣系数
     */
    private BigDecimal scale;
    /**
     * 折扣金额上限
     */
    private BigDecimal discountLimit;

    public AbsFixedScaleDiscount() {
        this(BigDecimal.ONE);
    }

    public AbsFixedScaleDiscount(BigDecimal scale,
                                 BigDecimal discountLimit) {
        this.scale = scale;
        this.discountLimit = discountLimit;
    }

    public AbsFixedScaleDiscount(BigDecimal scale) {
        this.scale = scale;
    }

    @Override
    public BigDecimal _handle(GeneralOrder order, List<GeneralProduct> usedProduct) {
        if (usedProduct.isEmpty()) {
            return BigDecimal.ZERO;
        }
        // 获取匹配的商品优惠前的价格
        BigDecimal discountTotalAmount = getPreferentialTotalPrice(usedProduct);

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
            BigDecimal preDiscountPrice = product.getPreDiscountPrice();
            BigDecimal preferentialPrice = product.getPreferentialPrice();
            BigDecimal discountPrice = product.getDiscountPrice();

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

            updateProduct(product, useDiscountPrice);

            GLog.addLog(String.format("统计: 优惠金额=%s", useDiscountPrice));
        }

        return useTotalDiscountPrice;
    }

    /**
     * 计算已匹配商品的优惠总金额
     *
     * @param usedProduct
     * @return
     */
    private BigDecimal getPreferentialTotalPrice(List<GeneralProduct> usedProduct) {
        return usedProduct.stream().map(AbsProduct::getPreferentialPrice)
            .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public void updateProduct(GeneralProduct product, BigDecimal useDiscountPrice) {
        if (BigDecimal.ZERO.compareTo(useDiscountPrice) == 0) {
            return;
        }
        // 重点1 - 处理优惠券
        product.addUseDiscount(this);
        // 重点2 - 处理折扣金额
        product.setDiscountPrice(product.getDiscountPrice().add(useDiscountPrice));
    }

    @Override
    public String toStrings(int spaceCount) {
        String space = LangUtils.getSpace(spaceCount);
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add(String.format("%s=>固定比例优惠券", space))
            .add(String.format("%s  优惠券ID: ", space) + id())
            .add(String.format("%s  优惠券名称: ", space) + title())
            .add(String.format("%s  券类型: ", space) + getClass())
            .add(String.format("%s  优惠券折扣比例: ", space) + getScale() + ", 折扣上限金额: " + getDiscountLimit())
            .add(String.format("%s  优惠券的总优惠金额: ", space) + getDiscountTotalAmount());
        return stringJoiner.toString();
    }
}
