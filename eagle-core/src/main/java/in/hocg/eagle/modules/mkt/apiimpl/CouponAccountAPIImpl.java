package in.hocg.eagle.modules.mkt.apiimpl;

import in.hocg.eagle.modules.mkt.api.CouponAccountAPI;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/6/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CouponAccountAPIImpl implements CouponAccountAPI {
    private final CouponAccountService service;

    @Override
    public CouponAccountComplexVo selectOne(Long userCouponId) {
        return service.selectOne(userCouponId);
    }

    @Override
    public boolean updateUsedStatus(Long userCouponId, BigDecimal couponAmount) {
        return service.updateUsedStatus(userCouponId, couponAmount);
    }

    @Override
    public void updateUnusedStatus(Long accountCouponId) {
        service.updateUnusedStatus(accountCouponId);
    }
}
