package in.hocg.eagle.modules.mkt.apimpl;

import in.hocg.basic.api.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.mkt.api.CouponAccountAPI;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CouponAccountAPIImpl implements CouponAccountAPI {
    private final CouponAccountService service;

    @Override
    public CouponAccountComplexVo selectOne(Long id) {
        return service.selectOne(id);
    }

    @Override
    public boolean updateUsedStatus(Long userCouponId, BigDecimal couponAmount) {
        return service.updateUsedStatus(userCouponId, couponAmount);
    }

    @Override
    public boolean updateUnusedStatus(Long accountCouponId) {
        return service.updateUnusedStatus(accountCouponId);
    }
}
