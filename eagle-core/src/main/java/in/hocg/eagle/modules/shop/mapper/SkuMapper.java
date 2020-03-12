package in.hocg.eagle.modules.shop.mapper;

import in.hocg.eagle.modules.shop.entity.Sku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [Shop模块] 商品SKU表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Mapper
public interface SkuMapper extends BaseMapper<Sku> {

    void deleteAllByProductId(@Param("productId") Long productId);

    List<Sku> selectListByProductId2(@Param("productId") Long productId);
}
