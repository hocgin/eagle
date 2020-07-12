package in.hocg.eagle.modules.oms.helper.order;


import com.google.common.collect.Maps;
import in.hocg.eagle.modules.oms.helper.order.modal.AbsProduct;
import in.hocg.eagle.modules.oms.helper.order.modal.Discount;
import in.hocg.eagle.utils.LangUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
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
public class GeneralProduct extends AbsProduct {
    @ApiModelProperty("商品ID")
    private Long productId;
    @ApiModelProperty("商品图片")
    private String productPic;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("销售价格")
    private BigDecimal productPrice;
    @ApiModelProperty("商品SKU ID")
    private Long productSkuId;
    @ApiModelProperty("商品SKU条码")
    private String productSkuCode;
    @ApiModelProperty("商品规格")
    private String productSpecData;
    @ApiModelProperty("商品品类ID")
    private Long productCategoryId;

    @ApiModelProperty("购买数量")
    private Integer productQuantity;
    /**
     * 保存每个优惠券在这个商品上优惠到金额
     * <优惠券类型:id, 优惠金额>
     */
    private final Map<Serializable, BigDecimal> useDiscountPriceMaps = Maps.newHashMap();

    public GeneralProduct(BigDecimal productPrice, Integer productQuantity) {
        super(productPrice.multiply(new BigDecimal(productQuantity)));
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    /**
     * [保存每种优惠优惠的金额] 禁止外部调用
     * @param discount
     * @param useDiscountPrice
     */
    protected void keepUseDiscountPrice(Discount discount, BigDecimal useDiscountPrice) {
        final String key = discount.getClass().getName() + ":" + discount.id();
        this.useDiscountPriceMaps.put(key, useDiscountPrice);
    }

    public Optional<BigDecimal> getUseDiscountPrice(Discount discount) {
        final String key = discount.getClass().getName() + ":" + discount.id();
        return Optional.ofNullable(this.useDiscountPriceMaps.get(key));
    }

    public Optional<BigDecimal> getUseDiscountPrice(Class<Discount> discountClass) {
        final String prefixKey = discountClass.getName() + ":";
        BigDecimal total = null;
        for (Map.Entry<Serializable, BigDecimal> entry : this.useDiscountPriceMaps.entrySet()) {
            final String key = (String) entry.getKey();
            final BigDecimal value = entry.getValue();
            if (key.startsWith(prefixKey)) {
                total = LangUtils.getOrDefault(total, BigDecimal.ZERO).add(LangUtils.getOrDefault(value, BigDecimal.ZERO));
            }
        }
        return Optional.ofNullable(total);
    }

    public String toStrings() {
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("  =>商品的ID:" + this.getProductId())
            // 课程信息
            .add("    商品名称:" + this.getProductName())
            .add("    商品规格:" + this.getProductSpecData())
            .add("    商品单价:" + this.getProductPrice())
            .add("    商品数量:" + this.getProductQuantity())
            // 金额信息
            .add("    商品的原价格(单价*数量):" + this.getPreDiscountPrice())
            .add("    商品优惠的额度:-" + (this.getPreDiscountPrice().subtract(this.getPreferentialPrice())))
            .add("    商品的固定费用(不参与优惠):" + this.getFixedCostsPrice());

        // 商品使用的优惠
        this.getUseDiscount().forEach(item->{
            stringJoiner.add(item.toStrings(2));
        });

        return stringJoiner.toString();
    }
}
