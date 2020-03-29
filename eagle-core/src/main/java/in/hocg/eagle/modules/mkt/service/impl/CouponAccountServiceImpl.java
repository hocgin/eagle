package in.hocg.eagle.modules.mkt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.CouponUseStatus;
import in.hocg.eagle.basic.constant.datadict.CouponUseType;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.lang.SNCode;
import in.hocg.eagle.mapstruct.CouponAccountMapping;
import in.hocg.eagle.mapstruct.CouponMapping;
import in.hocg.eagle.modules.mkt.entity.Coupon;
import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.modules.mkt.entity.CouponProductCategoryRelation;
import in.hocg.eagle.modules.mkt.entity.CouponProductRelation;
import in.hocg.eagle.modules.mkt.mapper.CouponAccountMapper;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import in.hocg.eagle.modules.mkt.pojo.qo.GiveCouponQo;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.modules.mkt.service.CouponProductCategoryRelationService;
import in.hocg.eagle.modules.mkt.service.CouponProductRelationService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final CouponProductRelationService couponProductRelationService;
    private final CouponProductCategoryRelationService couponProductCategoryRelationService;
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

    private CouponAccountComplexVo convertCouponAccountComplex(CouponAccount entity) {
        final Long couponId = entity.getCouponId();
        final Coupon coupon = couponService.getById(couponId);
        final CouponAccountComplexVo result = couponMapping.asCouponAccountComplexVo(entity, coupon);
        if (LangUtils.equals(CouponUseType.DesignatedProduct.getCode(), result.getUseType())) {
            final List<Long> productIds = couponProductRelationService.selectAllByCouponId(couponId).parallelStream().map(CouponProductRelation::getProductId).collect(Collectors.toList());
            result.setCanUseProductId(productIds);
        } else if (LangUtils.equals(CouponUseType.DesignatedProduct.getCode(), result.getUseType())) {
            final List<Long> productCategoryIds = couponProductCategoryRelationService.selectAllByCouponId(couponId).parallelStream().map(CouponProductCategoryRelation::getProductCategoryId).collect(Collectors.toList());
            result.setCanUseProductCategoryId(productCategoryIds);
        } else if (LangUtils.equals(CouponUseType.Universal.getCode(), result.getUseType())) {
            // 通用类型的优惠券
        } else {
            throw ServiceException.wrap("优惠券使用类型异常");
        }
        return result;
    }

    @Override
    public void validEntity(CouponAccount entity) {
        final Long couponId = entity.getCouponId();
        if (Objects.nonNull(couponId)) {
            ValidUtils.notNull(couponService.getById(couponId), "未找到优惠券");
        }
    }
}
