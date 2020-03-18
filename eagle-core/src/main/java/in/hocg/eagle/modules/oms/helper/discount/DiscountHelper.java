package in.hocg.eagle.modules.oms.helper.discount;

import in.hocg.eagle.modules.oms.helper.discount.coupon.Coupon;
import in.hocg.eagle.modules.oms.helper.discount.coupon.FixedAmountCoupon;
import in.hocg.eagle.modules.oms.helper.discount.coupon.FixedScaleCoupon;
import in.hocg.eagle.basic.constant.datadict.CouponType;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.oms.pojo.vo.coupon.CouponAccountComplexVo;
import in.hocg.eagle.utils.LangUtils;
import lombok.experimental.UtilityClass;

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
    public static Coupon createCoupon(CouponAccountComplexVo vo) {
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
    private static FixedAmountCoupon createFixedAmountCoupon(CouponAccountComplexVo vo) {
        return new FixedAmountCoupon(vo.getCredit())
            .setCanUseProductCategoryId(vo.getCanUseProductCategoryId())
            .setCanUseProductId(vo.getCanUseProductId())
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
    private static FixedScaleCoupon createFixedScaleCoupon(CouponAccountComplexVo vo) {
        return new FixedScaleCoupon(vo.getCredit(), vo.getCeiling())
            .setCanUseProductCategoryId(vo.getCanUseProductCategoryId())
            .setCanUseProductId(vo.getCanUseProductId())
            .setMinPoint(vo.getMinPoint())
            .setStartAt(vo.getStartAt())
            .setEndAt(vo.getEndAt())
            .setUseStatus(vo.getUseStatus())
            .setPlatform(vo.getPlatform())
            .setTitle(vo.getTitle())
            .setId(vo.getId());
    }

}
