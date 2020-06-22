package in.hocg.eagle.modules.mkt.mapstruct;

import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.modules.mkt.pojo.qo.GiveCouponQo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/3/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface CouponAccountMapping {
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "couponSn", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "useStatus", ignore = true)
    @Mapping(target = "usedAt", ignore = true)
    @Mapping(target = "couponId", ignore = true)
    @Mapping(target = "actualAmount", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    CouponAccount asCouponAccount(GiveCouponQo qo);
}
