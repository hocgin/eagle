package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.mkt.entity.Coupon;
import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponSaveQo;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponComplexVo;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface CouponMapping {
    @Mapping(target = "useTypeName", ignore = true)
    @Mapping(target = "useStatusName", ignore = true)
    @Mapping(target = "platformName", ignore = true)
    @Mapping(target = "couponTypeName", ignore = true)
    @Mapping(target = "canUseProductId", ignore = true)
    @Mapping(target = "canUseProductCategoryId", ignore = true)
    @Mapping(target = "accountId", source = "couponAccount.accountId")
    @Mapping(target = "id", source = "couponAccount.id")
    @Mapping(target = "endAt", source = "couponAccount.endAt")
    @Mapping(target = "usedAt", source = "couponAccount.usedAt")
    @Mapping(target = "useStatus", source = "couponAccount.useStatus")
    @Mapping(target = "startAt", source = "couponAccount.startAt")
    @Mapping(target = "couponSn", source = "couponAccount.couponSn")
    @Mapping(target = "useType", source = "coupon.useType")
    @Mapping(target = "title", source = "coupon.title")
    @Mapping(target = "platform", source = "coupon.platform")
    @Mapping(target = "minPoint", source = "coupon.minPoint")
    @Mapping(target = "instructions", source = "coupon.instructions")
    @Mapping(target = "credit", source = "coupon.credit")
    @Mapping(target = "couponType", source = "coupon.couponType")
    @Mapping(target = "ceiling", source = "coupon.ceiling")
    CouponAccountComplexVo asCouponAccountComplexVo(CouponAccount couponAccount, Coupon coupon);


    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Coupon asCoupon(CouponSaveQo qo);

    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "useTypeName", ignore = true)
    @Mapping(target = "platformName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    @Mapping(target = "couponTypeName", ignore = true)
    CouponComplexVo asCouponComplex(Coupon entity);
}
