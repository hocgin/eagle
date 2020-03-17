package in.hocg.eagle.modules.shop.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.shop.entity.Sku;
import in.hocg.eagle.modules.shop.pojo.vo.sku.SkuComplexVo;

import javax.validation.constraints.NotNull;
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

    /**
     * 检查并执行更新或新增
     *
     * @param productId
     * @param entities
     */
    void validInsertOrUpdateByProductId(@NotNull Long productId, List<Sku> entities);

    /**
     * 检查并使用库存
     *
     * @param skuId
     * @param useStock
     * @return true 成功 false 失败
     */
    boolean casValidAndSubtractStock(Long skuId, Integer useStock);

    /**
     * 释放使用的库存
     *
     * @param skuId
     * @param useStock
     * @return
     */
    boolean casValidAndPlusStock(Long skuId, Integer useStock);

    Sku selectOneBySkuIdAndValidQuantity(Long skuId, Integer quantity);
}
