package in.hocg.eagle.modules.mkt.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.mkt.entity.CouponProductRelation;
import in.hocg.eagle.modules.mkt.mapper.CouponProductRelationMapper;
import in.hocg.eagle.modules.mkt.service.CouponProductRelationService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.modules.pms.entity.Product;
import in.hocg.eagle.modules.pms.service.ProductService;
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
 * 优惠券可用商品表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CouponProductRelationServiceImpl extends AbstractServiceImpl<CouponProductRelationMapper, CouponProductRelation> implements CouponProductRelationService {
    private final CouponService couponService;
    private final ProductService productService;

    @Override
    public List<CouponProductRelation> selectAllByCouponId(Long couponId) {
        return lambdaQuery().eq(CouponProductRelation::getCouponId, couponId).list();
    }

    @Override
    public List<Product> selectAllProductByCouponId(Long couponId) {
        return baseMapper.selectAllProductByCouponId(couponId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void validInsertOrUpdateByCouponId(Long couponId, List<CouponProductRelation> entities) {
        final List<Long> allProductCategoryId = selectAllByCouponId(couponId).parallelStream()
            .map(CouponProductRelation::getId)
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
            .map(CouponProductRelation::getId).collect(Collectors.toList());

        // 需要删除的ID
        final List<Long> deleteIds = allProductCategoryId.parallelStream()
            .filter(id -> !updateIds.contains(id)).collect(Collectors.toList());
        this.removeByIds(deleteIds);
        entities.forEach(this::validInsertOrUpdate);
    }

    @Override
    public void validEntity(CouponProductRelation entity) {
        final Long productId = entity.getProductId();
        final Long couponId = entity.getCouponId();
        if (Objects.nonNull(productId)) {
            ValidUtils.notNull(productService.getById(productId), "商品不存在");
        }
        if (Objects.nonNull(couponId)) {
            ValidUtils.notNull(couponService.getById(couponId), "优惠券不存在");
        }
    }
}
