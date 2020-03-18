package in.hocg.eagle.modules.pms.service.impl;

import in.hocg.eagle.basic.datastruct.tree.Tree;
import in.hocg.eagle.basic.mybatis.tree.TreeServiceImpl;
import in.hocg.eagle.mapstruct.ProductCategoryMapping;
import in.hocg.eagle.modules.pms.entity.ProductCategory;
import in.hocg.eagle.modules.pms.mapper.ProductCategoryMapper;
import in.hocg.eagle.modules.pms.pojo.qo.category.ProductCategorySaveQo;
import in.hocg.eagle.modules.pms.pojo.qo.category.ProductCategorySearchQo;
import in.hocg.eagle.modules.pms.pojo.vo.category.ProductCategoryTreeVo;
import in.hocg.eagle.modules.pms.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<ProductCategoryTreeVo> tree(ProductCategorySearchQo qo) {
        final Long parentId = qo.getParentId();
        List<ProductCategory> all = baseMapper.search(qo);
        return Tree.getChild(parentId, all.stream()
            .map(mapping::asProductCategoryTreeVo)
            .collect(Collectors.toList()));
    }


    @Override
    public void validEntity(ProductCategory entity) {
        super.validEntity(entity);
    }
}
