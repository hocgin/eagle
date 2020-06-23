package in.hocg.eagle.modules.mkt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.basic.api.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.modules.mkt.mapper.CouponAccountMapper;
import in.hocg.eagle.modules.mkt.mapstruct.CouponAccountMapping;
import in.hocg.eagle.modules.mkt.mapstruct.CouponMapping;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import in.hocg.eagle.modules.mkt.pojo.qo.GiveCouponQo;
import in.hocg.basic.api.vo.CouponComplexVo;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.modules.ums.api.AccountAPI;
import in.hocg.web.AbstractServiceImpl;
import in.hocg.web.constant.datadict.CouponUseStatus;
import in.hocg.web.lang.SNCode;
import in.hocg.web.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final AccountAPI accountService;
    private final CouponMapping couponMapping;
    private final CouponAccountMapping mapping;
    private final SNCode snCode;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CouponAccountComplexVo selectOne(Long couponAccountId) {
        final CouponAccount entity = getById(couponAccountId);
        ValidUtils.notNull(entity, "未找到优惠券");
        return convertCouponAccountComplex(entity);
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
        return baseMapper.paging(qo, qo.page()).convert(this::convertCouponAccountComplex);
    }

    @Override
    public boolean updateUnusedStatus(Long accountCouponId) {
        final CouponAccount updated = new CouponAccount();
        updated.setId(accountCouponId);
        updated.setUseStatus(CouponUseStatus.Unused.getCode());
        return this.validUpdateById(updated);
    }

    private CouponAccountComplexVo convertCouponAccountComplex(CouponAccount entity) {
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
            ValidUtils.notNull(accountService.selectOne(accountId), "未找到用户");
        }
    }
}
