package in.hocg.eagle.modules.mkt.service;

import in.hocg.web.AbstractService;
import in.hocg.eagle.modules.mkt.entity.CouponProductCategoryRelation;
import in.hocg.eagle.modules.pms.entity.ProductCategory;

import java.util.List;

/**
 * <p>
 * 优惠券可用商品品类表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
public interface CouponProductCategoryRelationService extends AbstractService<CouponProductCategoryRelation> {

    List<CouponProductCategoryRelation> selectAllByCouponId(Long couponId);

    void validInsertOrUpdateByCouponId(Long couponId, List<CouponProductCategoryRelation> entities);

    List<ProductCategory> selectAllProductCategoryByCouponId(Long couponId);
}
