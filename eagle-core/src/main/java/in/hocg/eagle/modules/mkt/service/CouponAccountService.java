package in.hocg.eagle.modules.mkt.service;

import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.oms.pojo.vo.coupon.CouponAccountComplexVo;

import java.math.BigDecimal;

/**
 * <p>
 * 优惠券账号拥有人表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
public interface CouponAccountService extends AbstractService<CouponAccount> {

    CouponAccountComplexVo selectOne(Long couponAccountId);

    boolean updateUsedStatus(Long id, BigDecimal actualAmount);
}
