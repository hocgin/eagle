package in.hocg.eagle.modules.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.modules.pms.entity.Product;
import in.hocg.eagle.modules.pms.pojo.qo.ProductCompleteQo;
import in.hocg.eagle.modules.pms.pojo.qo.ProductPagingQo;
import in.hocg.eagle.modules.pms.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * [Shop模块] 商品表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
public interface ProductService extends AbstractService<Product> {

    @ApiOperation("查询正在销售的商品详情 - 商品")
    @Transactional(rollbackFor = Exception.class)
    ProductComplexVo getByShoppingAndId(Long id);

    @ApiOperation("查询正在销售的商品 - 商品列表")
    @Transactional(rollbackFor = Exception.class)
    IPage<ProductComplexVo> pagingByShopping(ProductPagingQo qo);

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

    List<ProductComplexVo> selectList(List<Long> ids);

    /**
     * 分页查询商品
     * @param qo
     * @return
     */
    IPage<ProductComplexVo> paging(ProductPagingQo qo);

    Product selectOneByIdAndNotDeleted(Long id);

    IPage<ProductComplexVo> pagingWithComplete(ProductCompleteQo qo);
}
