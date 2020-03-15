package in.hocg.eagle.modules.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.shop.entity.Product;
import in.hocg.eagle.modules.shop.pojo.qo.ProductPagingQo;
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

    /**
     * 分页查询商品
     * @param qo
     * @return
     */
    IPage<ProductComplexVo> paging(ProductPagingQo qo);

}
