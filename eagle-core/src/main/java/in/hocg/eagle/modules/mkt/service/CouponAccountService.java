package in.hocg.eagle.modules.mkt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractService;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import in.hocg.eagle.modules.mkt.pojo.qo.GiveCouponQo;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;

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

    void giveToUsers(GiveCouponQo qo);

    IPage<CouponAccountComplexVo> paging(CouponAccountPagingQo qo);

    void updateUnusedStatus(Long accountCouponId);
}
