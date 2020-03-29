package in.hocg.eagle.modules.mkt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.CouponUseType;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.mapstruct.CouponMapping;
import in.hocg.eagle.modules.mkt.entity.Coupon;
import in.hocg.eagle.modules.mkt.entity.CouponProductCategoryRelation;
import in.hocg.eagle.modules.mkt.entity.CouponProductRelation;
import in.hocg.eagle.modules.mkt.mapper.CouponMapper;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponPagingQo;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponSaveQo;
import in.hocg.eagle.modules.mkt.pojo.qo.GiveCouponQo;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponComplexVo;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.modules.mkt.service.CouponProductCategoryRelationService;
import in.hocg.eagle.modules.mkt.service.CouponProductRelationService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final CouponMapping mapping;
    private final CouponAccountService couponAccountService;
    private final CouponProductCategoryRelationService couponProductCategoryRelationService;
    private final CouponProductRelationService couponProductRelationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<CouponComplexVo> paging(CouponPagingQo qo) {
        return baseMapper.paging(qo, qo.page()).convert(this::convertCouponComplex);
    }

    @Override
    public CouponComplexVo selectOne(IdQo qo) {
        final Long id = qo.getId();
        final Coupon entity = getById(id);
        ValidUtils.notNull(entity, "未找到优惠券");
        return convertCouponComplex(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void giveToUsers(GiveCouponQo qo) {
        couponAccountService.giveToUsers(qo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(CouponSaveQo qo) {
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();
        Coupon entity = mapping.asCoupon(qo);
        if (Objects.isNull(entity.getId())) {
            entity.setCreatedAt(createdAt);
            entity.setCreator(userId);
        } else {
            entity.setLastUpdatedAt(createdAt);
            entity.setLastUpdater(userId);
        }
        validInsertOrUpdate(entity);

        final CouponUseType useType = IntEnum.ofThrow(qo.getUseType(), CouponUseType.class);
        handleCouponUseType(entity.getId(), useType, qo.getUseTargetId());
    }

    /**
     * 处理关联优惠券优惠
     *
     * @param couponId
     * @param useType
     * @param targetId
     */
    private void handleCouponUseType(@NotNull Long couponId, @NotNull CouponUseType useType, List<Long> targetId) {
        switch (useType) {
            case Universal: {
                return;
            }
            case SpecifiedCategory: {
                ValidUtils.notNull(targetId);
                couponProductCategoryRelationService.validInsertOrUpdateByCouponId(couponId, targetId.parallelStream()
                    .map(productCategoryId -> new CouponProductCategoryRelation()
                        .setCouponId(couponId)
                        .setProductCategoryId(productCategoryId))
                    .collect(Collectors.toList()));
                break;
            }
            case DesignatedProduct: {
                ValidUtils.notNull(targetId);
                couponProductRelationService.validInsertOrUpdateByCouponId(couponId, targetId.parallelStream()
                    .map(productId -> new CouponProductRelation()
                        .setCouponId(couponId)
                        .setProductId(productId))
                    .collect(Collectors.toList()));
                break;
            }
            default: {
                ValidUtils.fail("错误的优惠券使用类型");
            }
        }
    }

    private CouponComplexVo convertCouponComplex(Coupon entity) {
        return mapping.asCouponComplex(entity);
    }
}
