package in.hocg.eagle.modules.mkt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.mkt.entity.CouponProductCategoryRelation;
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

    List<CouponProductCategoryRelation> selectAllProductCategoryByCouponId(@Param("couponId") Long couponId);
}
