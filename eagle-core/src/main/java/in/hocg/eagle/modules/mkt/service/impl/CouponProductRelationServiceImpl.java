package in.hocg.eagle.modules.mkt.service.impl;

import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.modules.mkt.entity.CouponProductRelation;
import in.hocg.eagle.modules.mkt.mapper.CouponProductRelationMapper;
import in.hocg.eagle.modules.mkt.service.CouponProductRelationService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import in.hocg.eagle.modules.pms.service.ProductService;
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
    public List<ProductComplexVo> selectAllProductByCouponId(Long couponId) {
        final List<Long> idList = this.selectAllByCouponId(couponId).parallelStream()
            .map(CouponProductRelation::getProductId)
            .collect(Collectors.toList());
        return productService.selectList(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void validInsertOrUpdateByCouponId(Long couponId, List<CouponProductRelation> entities) {
        final List<CouponProductRelation> allData = this.selectAllByCouponId(couponId);

        final BiFunction<CouponProductRelation, CouponProductRelation, Boolean> isSame =
            (t1, t2) -> LangUtils.equals(t1.getId(), t2.getId());
        final List<CouponProductRelation> mixedList = LangUtils.getMixed(allData, entities, isSame);
        List<CouponProductRelation> deleteList = LangUtils.removeIfExits(allData, mixedList, isSame);
        List<CouponProductRelation> addList = LangUtils.removeIfExits(entities, mixedList, isSame);

        // 删除
        this.removeByIds(deleteList.parallelStream()
            .map(CouponProductRelation::getId)
            .collect(Collectors.toList()));

        // 新增
        addList.forEach(this::validInsertOrUpdate);

        // 更新
        mixedList.forEach(this::validInsertOrUpdate);
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
