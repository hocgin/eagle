package in.hocg.eagle.modules.shop.service.impl;

import in.hocg.eagle.basic.mybatis.tree.TreeServiceImpl;
import in.hocg.eagle.mapstruct.ProductCategoryMapping;
import in.hocg.eagle.modules.shop.entity.ProductCategory;
import in.hocg.eagle.modules.shop.mapper.ProductCategoryMapper;
import in.hocg.eagle.modules.shop.pojo.qo.category.ProductCategorySaveQo;
import in.hocg.eagle.modules.shop.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
public class ProductCategoryServiceImpl extends TreeServiceImpl<ProductCategoryMapper, ProductCategory>
    implements ProductCategoryService {
    private final ProductCategoryMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(ProductCategorySaveQo qo) {
        ProductCategory entity = mapping.asProductCategory(qo);
        if (Objects.isNull(qo.getId())) {
            entity.setCreatedAt(qo.getCreatedAt());
            entity.setCreator(qo.getUserId());
        } else {
            entity.setLastUpdatedAt(qo.getCreatedAt());
            entity.setLastUpdater(qo.getUserId());
        }

        validInsertOrUpdate(entity);
    }


    @Override
    public void validEntity(ProductCategory entity) {
        super.validEntity(entity);
    }
}
