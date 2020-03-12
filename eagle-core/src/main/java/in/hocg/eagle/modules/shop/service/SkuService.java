package in.hocg.eagle.modules.shop.service;

import in.hocg.eagle.modules.shop.entity.Sku;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.shop.pojo.vo.sku.SkuComplexVo;

import java.util.List;

/**
 * <p>
 * [Shop模块] 商品SKU表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
public interface SkuService extends AbstractService<Sku> {

    void deleteAllByProductId(Long productId);

    List<SkuComplexVo> selectListByProductId(Long productId);
}
