package in.hocg.eagle.modules.oms.helper.order.discount.coupon;


import com.google.common.collect.Lists;
import in.hocg.eagle.modules.oms.helper.order.AbsFixedAmountDiscount;
import in.hocg.eagle.modules.oms.helper.order.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order.GeneralProduct;
import in.hocg.web.constant.datadict.CouponUseStatus;
import in.hocg.web.constant.datadict.OrderSourceType;
import in.hocg.web.utils.LangUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FixedAmountCoupon extends AbsFixedAmountDiscount
    implements Coupon {

    @ApiModelProperty("用户优惠券ID")
    private Long id;
    @ApiModelProperty("优惠名称")
    private String title;
    @ApiModelProperty("使用状态：[0:未使用；1:已使用；2:已过期]")
    private Integer useStatus;
    @ApiModelProperty("优惠券生效时间")
    private LocalDateTime startAt;
    @ApiModelProperty("优惠券失效时间")
    private LocalDateTime endAt;
    @ApiModelProperty("订单最低启用金额")
    private BigDecimal minPoint;
    @ApiModelProperty("可用平台：[1:移动；2:PC]")
    private Integer platform;
    @ApiModelProperty("可用商品品类")
    private List<Long> canUseProductCategoryId;
    @ApiModelProperty("可用商品")
    private List<Long> canUseProductId;

    public FixedAmountCoupon(BigDecimal val) {
        super(val);
    }



    @Override
    public List<GeneralProduct> match(GeneralOrder order) {
        LocalDateTime createdAt = order.getCreateAt();
        final OrderSourceType sourceType = order.getSourceType();
        final List<GeneralProduct> products = order.getProducts();

        // 满足使用平台
        final boolean isPlatform = Objects.isNull(platform) || LangUtils.equals(sourceType.getCode(), platform);

        // 满足未使用
        final boolean isUnused = LangUtils.equals(CouponUseStatus.Unused.getCode(), useStatus);

        // 满足生效日期范围
        boolean isUsableDate = startAt.isBefore(createdAt)
            && createdAt.isBefore(endAt);
        BigDecimal orderPrice = order.getPreDiscountTotalAmount();

        // 满足最低启用金额
        boolean isUsableAvailablePrice = Objects.isNull(minPoint) || minPoint.compareTo(orderPrice) <= 0;

        // 所有商品满足使用条件
        boolean hasCanUseProductId = true;
        if (Objects.nonNull(canUseProductId)) {
            hasCanUseProductId = products.parallelStream().map(GeneralProduct::getProductId)
                .allMatch(productId -> canUseProductId.contains(productId));
        }

        // 所有商品品类满足使用条件
        boolean hasCanUseProductCategoryId = true;
        if (Objects.nonNull(canUseProductCategoryId)) {
            hasCanUseProductCategoryId = products.parallelStream().map(GeneralProduct::getProductId)
                .anyMatch(productId -> canUseProductCategoryId.contains(productId));
        }

        // 需全部满足
        if (isPlatform && isUnused && isUsableDate && isUsableAvailablePrice && hasCanUseProductId && hasCanUseProductCategoryId) {
            return products;
        }

        return Lists.newArrayList();
    }

    @Override
    public Serializable id() {
        return this.id;
    }

    @Override
    public String title() {
        return this.title;
    }
}
