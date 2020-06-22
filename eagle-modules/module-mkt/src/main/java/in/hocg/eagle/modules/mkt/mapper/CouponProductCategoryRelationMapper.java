package in.hocg.eagle.modules.mkt.mapper;

import in.hocg.eagle.modules.mkt.entity.CouponProductCategoryRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.pms.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券可用商品品类表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Mapper
public interface CouponProductCategoryRelationMapper extends BaseMapper<CouponProductCategoryRelation> {

    List<ProductCategory> selectAllProductCategoryByCouponId(@Param("couponId") Long couponId);
}
