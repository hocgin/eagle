package in.hocg.eagle.modules.mkt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.mkt.entity.Coupon;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 优惠券表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {

    IPage<Coupon> paging(@Param("qo") CouponPagingQo qo, @Param("page") Page page);
}
