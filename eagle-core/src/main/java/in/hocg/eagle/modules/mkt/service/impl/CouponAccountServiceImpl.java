package in.hocg.eagle.modules.mkt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.constant.datadict.CouponUseStatus;
import in.hocg.eagle.basic.ext.lang.SNCode;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.modules.mkt.mapper.CouponAccountMapper;
import in.hocg.eagle.modules.mkt.mapstruct.CouponAccountMapping;
import in.hocg.eagle.modules.mkt.mapstruct.CouponMapping;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import in.hocg.eagle.modules.mkt.pojo.qo.GiveCouponQo;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponComplexVo;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    private final CouponService couponService;
    private final AccountService accountService;
    private final CouponMapping couponMapping;
    private final CouponAccountMapping mapping;
    private final SNCode snCode;

    @Override
    @ApiOperation("查询我的优惠券 - 用户优惠券")
    @Transactional(rollbackFor = Exception.class)
    public IPage<CouponAccountComplexVo> pagingMyCoupon(CouponAccountPagingQo qo) {
        qo.setAccountId(qo.getUserId());
        return this.paging(qo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CouponAccountComplexVo selectOne(Long couponAccountId) {
        final CouponAccount entity = getById(couponAccountId);
        ValidUtils.notNull(entity, "未找到优惠券");
        return convertComplex(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUsedStatus(Long id, BigDecimal actualAmount) {
        return lambdaUpdate().set(CouponAccount::getUsedAt, LocalDateTime.now())
            .set(CouponAccount::getUseStatus, CouponUseStatus.Used.getCode())
            .set(CouponAccount::getActualAmount, actualAmount)
            .eq(CouponAccount::getId, id)
            .eq(CouponAccount::getUseStatus, CouponUseStatus.Unused.getCode()).update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void giveToUsers(GiveCouponQo qo) {
        CouponAccount entity = mapping.asCouponAccount(qo);
        entity.setCouponId(qo.getId());
        entity.setCreatedAt(qo.getCreatedAt());
        entity.setCreator(qo.getUserId());
        entity.setUseStatus(CouponUseStatus.Unused.getCode());

        for (Long userId : qo.getAccountId()) {
            entity.setAccountId(userId);
            entity.setCouponSn(snCode.getCouponAccountSNCode());
            validInsert(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<CouponAccountComplexVo> paging(CouponAccountPagingQo qo) {
        return baseMapper.paging(qo, qo.page()).convert(this::convertComplex);
    }

    @Override
    public void updateUnusedStatus(Long accountCouponId) {
        final CouponAccount updated = new CouponAccount();
        updated.setId(accountCouponId);
        updated.setUseStatus(CouponUseStatus.Unused.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CouponAccountComplexVo> selectListByAccountId(Long userId) {
        return LangUtils.toList(this.lambdaQuery().eq(CouponAccount::getAccountId, userId).list(), this::convertComplex);
    }

    @Override
    public List<CouponAccountComplexVo> selectListByAccountIdAndUsable(Long userId) {
        return LangUtils.toList(baseMapper.selectListByAccountIdAndUsable(userId), this::convertComplex);
    }

    private CouponAccountComplexVo convertComplex(CouponAccount entity) {
        final Long couponId = entity.getCouponId();
        final CouponComplexVo coupon = couponService.selectOne(couponId);
        return couponMapping.asCouponAccountComplexVo(entity, coupon);
    }

    @Override
    public void validEntity(CouponAccount entity) {
        final Long couponId = entity.getCouponId();
        final Long accountId = entity.getAccountId();
        if (Objects.nonNull(couponId)) {
            ValidUtils.notNull(couponService.getById(couponId), "未找到优惠券");
        }
        if (Objects.nonNull(accountId)) {
            ValidUtils.notNull(accountService.getById(accountId), "未找到用户");
        }
    }
}
