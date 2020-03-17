package in.hocg.eagle.modules.mkt.service.impl;

import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.modules.mkt.mapper.CouponAccountMapper;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 优惠券账号拥有人表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CouponAccountServiceImpl extends AbstractServiceImpl<CouponAccountMapper, CouponAccount> implements CouponAccountService {

}
