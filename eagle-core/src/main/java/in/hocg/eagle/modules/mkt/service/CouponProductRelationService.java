package in.hocg.eagle.modules.mkt.service;

import in.hocg.web.AbstractService;
import in.hocg.eagle.modules.mkt.entity.CouponProductRelation;
import in.hocg.eagle.modules.pms.entity.Product;

import java.util.List;

/**
 * <p>
 * 优惠券可用商品表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
public interface CouponProductRelationService extends AbstractService<CouponProductRelation> {

    List<CouponProductRelation> selectAllByCouponId(Long couponId);

    List<Product> selectAllProductByCouponId(Long couponId);

    void validInsertOrUpdateByCouponId(Long couponId, List<CouponProductRelation> entities);
}
