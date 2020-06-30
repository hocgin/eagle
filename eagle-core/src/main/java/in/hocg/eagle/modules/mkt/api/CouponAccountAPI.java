package in.hocg.eagle.modules.mkt.api;

import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/6/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface CouponAccountAPI {
    CouponAccountComplexVo selectOne(Long userCouponId);

    boolean updateUsedStatus(Long userCouponId, BigDecimal couponAmount);

    void updateUnusedStatus(Long accountCouponId);
}
