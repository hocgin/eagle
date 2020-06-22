package in.hocg.eagle.modules.pms.service.impl;

import com.google.common.collect.Lists;
import in.hocg.web.datastruct.tree.Tree;
import in.hocg.web.exception.ServiceException;
import in.hocg.web.mybatis.tree.TreeServiceImpl;
import in.hocg.eagle.modules.pms.mapstruct.ProductCategoryMapping;
import in.hocg.eagle.modules.pms.entity.ProductCategory;
import in.hocg.eagle.modules.pms.mapper.ProductCategoryMapper;
import in.hocg.eagle.modules.pms.pojo.qo.category.ProductCategorySaveQo;
import in.hocg.eagle.modules.pms.pojo.qo.category.ProductCategorySearchQo;
import in.hocg.eagle.modules.pms.pojo.vo.category.ProductCategoryComplexVo;
import in.hocg.eagle.modules.pms.pojo.vo.category.ProductCategoryTreeVo;
import in.hocg.eagle.modules.pms.service.ProductCategoryService;
import in.hocg.eagle.modules.pms.service.ProductService;
import in.hocg.web.utils.LangUtils;
import in.hocg.web.utils.ValidUtils;
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
    private final ProductService productService;

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
    @Transactional(rollbackFor = Exception.class)
    public List<ProductCategoryTreeVo> tree(ProductCategorySearchQo qo) {
        final Long parentId = qo.getParentId();
        List<ProductCategory> all = baseMapper.search(qo);
        return Tree.getChild(parentId, all.parallelStream()
            .map(mapping::asProductCategoryTreeVo)
            .collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductCategoryComplexVo selectOne(Long id) {
        final ProductCategory entity = getById(id);
        ValidUtils.notNull(entity);
        return convertProductCategoryComplex(entity);
    }

    @Override
    public ProductCategoryComplexVo convertProductCategoryComplex(ProductCategory entity) {
        final ProductCategoryComplexVo result = mapping.asProductCategoryComplexVo(entity);
        result.setKeywords((List<String>) LangUtils.getOrDefault(entity.getKeywords(), Lists.newArrayList()));
        return result;
    }

    @Override
    public void deleteAll(Long id) {
        if (Objects.isNull(id)) {
            return;
        }
        final ProductCategory entity = getById(id);
        if (Objects.isNull(entity)) {
            return;
        }

        final String regexTreePath = String.format("%s.*?", entity.getTreePath());
        checkUsedThrowMessage(regexTreePath);

        super.deleteCurrentAndChildren(id);
    }

    public void checkUsedThrowMessage(String regexTreePath) {
        if (isUsedProduct(regexTreePath)) {
            throw ServiceException.wrap("商品品类正在被商品使用");
        }
        if (isUsedCoupon(regexTreePath)) {
            throw ServiceException.wrap("商品品类正在被优惠券使用");
        }
    }

    public boolean isUsedProduct(String regexTreePath) {
        return baseMapper.countUsedProduct(regexTreePath) > 0;
    }

    public boolean isUsedCoupon(String regexTreePath) {
        return baseMapper.countUsedCoupon(regexTreePath) > 0;
    }

    @Override
    public void validEntity(ProductCategory entity) {
        super.validEntity(entity);
    }
}