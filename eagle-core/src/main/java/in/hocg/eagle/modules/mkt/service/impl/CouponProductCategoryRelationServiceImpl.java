package in.hocg.eagle.modules.mkt.service.impl;

import in.hocg.eagle.modules.mkt.entity.CouponProductCategoryRelation;
import in.hocg.eagle.modules.mkt.mapper.CouponProductCategoryRelationMapper;
import in.hocg.eagle.modules.mkt.service.CouponProductCategoryRelationService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    @Override
    public List<CouponProductCategoryRelation> selectAllByCouponId(Long couponId) {
        return lambdaQuery().eq(CouponProductCategoryRelation::getCouponId, couponId).list();
    }
}
