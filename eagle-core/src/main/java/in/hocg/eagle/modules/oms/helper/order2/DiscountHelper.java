package in.hocg.eagle.modules.oms.helper.order2;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.constant.datadict.CouponPlatformType;
import in.hocg.eagle.basic.constant.datadict.CouponType;
import in.hocg.eagle.basic.constant.datadict.CouponUseType;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.oms.helper.order2.discount.Discount;
import in.hocg.eagle.modules.oms.helper.order2.discount.coupon.FixedAmountCoupon;
import in.hocg.eagle.modules.oms.helper.order2.discount.coupon.FixedScaleCoupon;
import in.hocg.eagle.modules.oms.helper.order2.rule.Rule;
import in.hocg.eagle.modules.oms.helper.order2.rule.rule.*;
import in.hocg.eagle.modules.pms.api.vo.ProductCategoryComplexVo;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import in.hocg.eagle.utils.LangUtils;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2020/7/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class DiscountHelper {

    public Discount createCoupon(CouponAccountComplexVo vo) {
        final Integer couponType = vo.getCouponType();
        final BigDecimal credit = vo.getCredit();
        final List<Rule> rules = getCouponRules(vo);
        final Long id = vo.getId();
        if (CouponType.Credit.eq(couponType)) {
            return new FixedAmountCoupon(credit, rules).setId(id);
        } else if (CouponType.Discount.eq(couponType)) {
            return new FixedScaleCoupon(credit, vo.getCeiling(), rules).setId(id);
        }
        throw ServiceException.wrap("不支持优惠券类型");
    }

    private List<Rule> getCouponRules(CouponAccountComplexVo vo) {
        final List<Rule> rules = Lists.newArrayList();
        rules.add(new UsableDatetimeRangeRule(vo.getStartAt(), vo.getEndAt()));
        rules.add(new UsableStatusRule(vo.getUseStatus()));
        rules.add(new OrderMinPointRule(vo.getMinPoint()));
        final Integer useType = vo.getUseType();
        if (CouponUseType.DesignatedProduct.eq(useType)) {
            final List<Long> canUseProductId = ((List<ProductComplexVo>) LangUtils.getOrDefault(vo.getCanUseProduct(), Lists.newArrayList())).parallelStream()
                .map(ProductComplexVo::getId).collect(Collectors.toList());
            rules.add(new UsableProductRule(canUseProductId));
        } else if (CouponUseType.DesignatedProduct.eq(useType)) {
            final List<Long> canUseProductCategoryId = ((List<ProductCategoryComplexVo>) LangUtils.getOrDefault(vo.getCanUseProductCategory(), Lists.newArrayList())).parallelStream()
                .map(ProductCategoryComplexVo::getId).collect(Collectors.toList());
            rules.add(new UsableProductRule(canUseProductCategoryId));
        }
        if (!CouponPlatformType.All.eq(vo.getPlatform())) {
            rules.add(new UsablePlatformRule(vo.getPlatform()));
        }
        return rules;
    }

}
