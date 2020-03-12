package in.hocg.eagle.modules.shop.mapper;

import in.hocg.eagle.modules.shop.entity.SpecValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [Shop模块] 商品规格值表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Mapper
public interface SpecValueMapper extends BaseMapper<SpecValue> {

    void deleteAllByProductId(@Param("productId") Long productId);
}
