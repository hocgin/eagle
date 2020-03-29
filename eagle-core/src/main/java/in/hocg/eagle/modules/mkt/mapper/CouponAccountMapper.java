package in.hocg.eagle.modules.mkt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 优惠券账号拥有人表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Mapper
public interface CouponAccountMapper extends BaseMapper<CouponAccount> {

    IPage<CouponAccount> paging(@Param("qo") CouponAccountPagingQo qo, @Param("page") Page page);
}
