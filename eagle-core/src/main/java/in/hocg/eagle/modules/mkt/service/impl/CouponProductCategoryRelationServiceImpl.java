package in.hocg.eagle.modules.mkt.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.mkt.entity.CouponProductCategoryRelation;
import in.hocg.eagle.modules.mkt.mapper.CouponProductCategoryRelationMapper;
import in.hocg.eagle.modules.mkt.service.CouponProductCategoryRelationService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.modules.pms.entity.ProductCategory;
import in.hocg.eagle.modules.pms.service.ProductCategoryService;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券可用商品品类表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CouponProductCategoryRelationServiceImpl extends AbstractServiceImpl<CouponProductCategoryRelationMapper, CouponProductCategoryRelation> implements CouponProductCategoryRelationService {
    private final ProductCategoryService productCategoryService;
    private final CouponService couponService;

    @Override
    public List<CouponProductCategoryRelation> selectAllByCouponId(Long couponId) {
        return lambdaQuery().eq(CouponProductCategoryRelation::getCouponId, couponId).list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void validInsertOrUpdateByCouponId(Long couponId, List<CouponProductCategoryRelation> entities) {
        final List<Long> allProductCategoryId = selectAllByCouponId(couponId).parallelStream()
            .map(CouponProductCategoryRelation::getId)
            .collect(Collectors.toList());

        // 需要更新的 ID
        final List<Long> updateIds = entities.parallelStream()
            .peek(sku -> {
                final Long id = sku.getId();
                if (Objects.nonNull(id) && !allProductCategoryId.contains(id)) {
                    sku.setId(null);
                }
            })
            .filter(sku -> Objects.nonNull(sku.getId()))
            .map(CouponProductCategoryRelation::getId).collect(Collectors.toList());

        // 需要删除的ID
        final List<Long> deleteIds = allProductCategoryId.parallelStream()
            .filter(id -> !updateIds.contains(id)).collect(Collectors.toList());
        this.removeByIds(deleteIds);
        entities.forEach(this::validInsertOrUpdate);
    }

    @Override
    public List<ProductCategory> selectAllProductCategoryByCouponId(Long couponId) {
        return baseMapper.selectAllProductCategoryByCouponId(couponId);
    }

    @Override
    public void validEntity(CouponProductCategoryRelation entity) {
        final Long productCategoryId = entity.getProductCategoryId();
        final Long couponId = entity.getCouponId();
        if (Objects.nonNull(productCategoryId)) {
            ValidUtils.notNull(productCategoryService.getById(productCategoryId), "商品分类不存在");
        }
        if (Objects.nonNull(couponId)) {
            ValidUtils.notNull(couponService.getById(couponId), "优惠券不存在");
        }
    }
}
