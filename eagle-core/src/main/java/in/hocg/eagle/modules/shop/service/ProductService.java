package in.hocg.eagle.modules.shop.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.shop.entity.Product;
import in.hocg.eagle.modules.shop.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.shop.pojo.vo.product.ProductComplexVo;

/**
 * <p>
 * [Shop模块] 商品表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
public interface ProductService extends AbstractService<Product> {

    /**
     * 新增商品
     *
     * @param qo
     */
    void saveOne(ProductSaveQo qo);

    /**
     * 查看商品详情
     *
     * @param id
     * @return
     */
    ProductComplexVo selectOne(Long id);
}
