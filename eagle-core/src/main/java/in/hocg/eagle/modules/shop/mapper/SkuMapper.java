package in.hocg.eagle.modules.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.shop.entity.Sku;
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

    /**
     * 补充库存
     *
     * @param skuId    SKU ID
     * @param useStock 要使用的库存数量
     * @param preStock 原库存数量
     * @return
     */
    Integer plusStock(@Param("skuId") Long skuId,
                      @Param("useStock") Integer useStock,
                      @Param("preStock") Integer preStock);

    /**
     * 减掉库存
     *
     * @param skuId    SKU ID
     * @param useStock 要使用的库存数量
     * @param preStock 原库存数量
     * @return
     */
    Integer subtractStock(@Param("skuId") Long skuId,
                          @Param("useStock") Integer useStock,
                          @Param("preStock") Integer preStock);

}
