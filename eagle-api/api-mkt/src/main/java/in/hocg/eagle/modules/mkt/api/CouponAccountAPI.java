package in.hocg.eagle.modules.mkt.api;

import in.hocg.basic.api.vo.CouponAccountComplexVo;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface CouponAccountAPI {
    CouponAccountComplexVo selectOne(Long id);

    boolean updateUsedStatus(Long userCouponId, BigDecimal couponAmount);

    boolean updateUnusedStatus(Long accountCouponId);
}
