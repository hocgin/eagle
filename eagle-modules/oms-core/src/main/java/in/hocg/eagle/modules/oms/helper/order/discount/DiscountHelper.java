package in.hocg.eagle.modules.oms.helper.order.discount;

import com.google.common.collect.Lists;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.Coupon;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.FixedAmountCoupon;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.FixedScaleCoupon;
import in.hocg.web.constant.datadict.CouponType;
import in.hocg.web.exception.ServiceException;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.pms.pojo.vo.category.ProductCategoryComplexVo;
import in.hocg.eagle.modules.pms.pojo.vo.product.ProductComplexVo;
import in.hocg.web.utils.LangUtils;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class DiscountHelper {

    /**
     * 获取指定类型优惠券
     *
     * @param vo
     * @return
     */
    public Coupon createCoupon(CouponAccountComplexVo vo) {
        final Integer couponType = vo.getCouponType();
        if (LangUtils.equals(CouponType.Credit.getCode(), couponType)) {
            return createFixedAmountCoupon(vo);
        } else if (LangUtils.equals(CouponType.Discount.getCode(), couponType)) {
            return createFixedScaleCoupon(vo);
        }
        throw ServiceException.wrap("不支持优惠券类型");
    }

    /**
     * 固定金额优惠券
     *
     * @param vo
     * @return
     */
    private FixedAmountCoupon createFixedAmountCoupon(CouponAccountComplexVo vo) {
        final List<Long> canUseProductId = ((List<ProductComplexVo>) LangUtils.getOrDefault(vo.getCanUseProduct(), Lists.newArrayList())).parallelStream().map(ProductComplexVo::getId).collect(Collectors.toList());
        final List<Long> canUseProductCategoryId = ((List<ProductCategoryComplexVo>) LangUtils.getOrDefault(vo.getCanUseProductCategory(), Lists.newArrayList())).parallelStream().map(ProductCategoryComplexVo::getId).collect(Collectors.toList());
        return new FixedAmountCoupon(vo.getCredit())
            .setCanUseProductCategoryId(canUseProductCategoryId)
            .setCanUseProductId(canUseProductId)
            .setMinPoint(vo.getMinPoint())
            .setStartAt(vo.getStartAt())
            .setEndAt(vo.getEndAt())
            .setUseStatus(vo.getUseStatus())
            .setPlatform(vo.getPlatform())
            .setTitle(vo.getTitle())
            .setId(vo.getId());
    }

    /**
     * 固定折扣优惠券
     *
     * @param vo
     * @return
     */
    private FixedScaleCoupon createFixedScaleCoupon(CouponAccountComplexVo vo) {
        final List<Long> canUseProductId = ((List<ProductComplexVo>) LangUtils.getOrDefault(vo.getCanUseProduct(), Lists.newArrayList())).parallelStream().map(ProductComplexVo::getId).collect(Collectors.toList());
        final List<Long> canUseProductCategoryId = ((List<ProductCategoryComplexVo>) LangUtils.getOrDefault(vo.getCanUseProductCategory(), Lists.newArrayList())).parallelStream().map(ProductCategoryComplexVo::getId).collect(Collectors.toList());
        return new FixedScaleCoupon(vo.getCredit(), vo.getCeiling())
            .setCanUseProductCategoryId(canUseProductCategoryId)
            .setCanUseProductId(canUseProductId)
            .setMinPoint(vo.getMinPoint())
            .setStartAt(vo.getStartAt())
            .setEndAt(vo.getEndAt())
            .setUseStatus(vo.getUseStatus())
            .setPlatform(vo.getPlatform())
            .setTitle(vo.getTitle())
            .setId(vo.getId());
    }

}
