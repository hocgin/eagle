package in.hocg.eagle.modules.shop.service;

import in.hocg.eagle.basic.mybatis.tree.TreeService;
import in.hocg.eagle.modules.shop.entity.ProductCategory;
import in.hocg.eagle.modules.shop.pojo.qo.category.ProductCategorySaveQo;

/**
 * <p>
 * [Shop模块] 商品品类表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
public interface ProductCategoryService extends TreeService<ProductCategory> {

    /**
     * 新增或更新商品品类
     *
     * @param qo
     */
    void saveOne(ProductCategorySaveQo qo);
}
