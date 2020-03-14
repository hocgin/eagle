package in.hocg.eagle.modules.shop.service.impl;

import in.hocg.eagle.modules.shop.entity.ProductCategory;
import in.hocg.eagle.modules.shop.mapper.ProductCategoryMapper;
import in.hocg.eagle.modules.shop.service.ProductCategoryService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [Shop模块] 商品品类表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ProductCategoryServiceImpl extends AbstractServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

}
