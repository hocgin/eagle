package in.hocg.eagle.modules.oms.helper.order.discount;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.constant.datadict.CouponType;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.oms.helper.order.AbsFixedAmountDiscount;
import in.hocg.eagle.modules.oms.helper.order.AbsFixedScaleDiscount;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.Coupon;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.FixedAmountCoupon;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.FixedScaleCoupon;
import in.hocg.eagle.modules.oms.helper.order.modal.Discount;
import in.hocg.eagle.modules.pms.api.vo.ProductCategoryComplexVo;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import in.hocg.eagle.utils.LangUtils;
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

    public DiscountComplex convertComplex(Discount discount) {
        final DiscountComplex result = new DiscountComplex();
        if (discount instanceof AbsFixedAmountDiscount) {
            final AbsFixedAmountDiscount info = (AbsFixedAmountDiscount) discount;
            result.setVal(info.getVal());
            result.setType(DiscountComplex.Type.Discount);
        } else if (discount instanceof AbsFixedScaleDiscount) {
            final AbsFixedScaleDiscount info = (AbsFixedScaleDiscount) discount;
            result.setVal(info.getScale());
            result.setType(DiscountComplex.Type.Reduction);
        }
        result.setId((Long) discount.id());
        result.setTitle(discount.title());
        return result;
    }
}
