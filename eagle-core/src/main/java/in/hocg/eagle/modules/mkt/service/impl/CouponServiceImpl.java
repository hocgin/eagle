package in.hocg.eagle.modules.mkt.service.impl;

import in.hocg.eagle.modules.mkt.entity.Coupon;
import in.hocg.eagle.modules.mkt.mapper.CouponMapper;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 优惠券表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CouponServiceImpl extends AbstractServiceImpl<CouponMapper, Coupon> implements CouponService {

}
