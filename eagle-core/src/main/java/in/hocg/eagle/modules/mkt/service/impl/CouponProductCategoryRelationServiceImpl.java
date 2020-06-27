package in.hocg.eagle.modules.mkt.service.impl;

import in.hocg.eagle.basic.ext.mybatis.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.mkt.entity.CouponProductCategoryRelation;
import in.hocg.eagle.modules.mkt.mapper.CouponProductCategoryRelationMapper;
import in.hocg.eagle.modules.mkt.service.CouponProductCategoryRelationService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.modules.pms.pojo.vo.category.ProductCategoryComplexVo;
import in.hocg.eagle.modules.pms.service.ProductCategoryService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
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
        final List<CouponProductCategoryRelation> allData = this.selectAllByCouponId(couponId);

        final BiFunction<CouponProductCategoryRelation, CouponProductCategoryRelation, Boolean> isSame =
            (t1, t2) -> LangUtils.equals(t1.getId(), t2.getId());
        final List<CouponProductCategoryRelation> mixedList = LangUtils.getMixed(allData, entities, isSame);
        List<CouponProductCategoryRelation> deleteList = LangUtils.removeIfExits(allData, mixedList, isSame);
        List<CouponProductCategoryRelation> addList = LangUtils.removeIfExits(entities, mixedList, isSame);

        // 删除
        this.removeByIds(deleteList.parallelStream()
            .map(CouponProductCategoryRelation::getId)
            .collect(Collectors.toList()));

        // 新增
        addList.forEach(this::validInsertOrUpdate);

        // 更新
        mixedList.forEach(this::validInsertOrUpdate);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProductCategoryComplexVo> selectAllProductCategoryByCouponId(Long couponId) {
        final List<Long> idList = this.selectAllByCouponId(couponId).parallelStream()
            .map(CouponProductCategoryRelation::getProductCategoryId)
            .collect(Collectors.toList());
        return productCategoryService.selectList(idList);
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
