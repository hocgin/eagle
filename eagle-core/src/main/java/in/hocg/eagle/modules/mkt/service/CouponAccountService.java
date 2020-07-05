package in.hocg.eagle.modules.mkt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import in.hocg.eagle.modules.mkt.pojo.qo.GiveCouponQo;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 优惠券账号拥有人表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
public interface CouponAccountService extends AbstractService<CouponAccount> {

    @ApiOperation("查询我的优惠券 - 用户优惠券")
    @Transactional(rollbackFor = Exception.class)
    IPage<CouponAccountComplexVo> pagingMyCoupon(CouponAccountPagingQo qo);

    CouponAccountComplexVo selectOne(Long couponAccountId);

    boolean updateUsedStatus(Long id, BigDecimal actualAmount);

    void giveToUsers(GiveCouponQo qo);

    IPage<CouponAccountComplexVo> paging(CouponAccountPagingQo qo);

    void updateUnusedStatus(Long accountCouponId);

    List<CouponAccountComplexVo> selectListByAccountId(Long userId);
}
