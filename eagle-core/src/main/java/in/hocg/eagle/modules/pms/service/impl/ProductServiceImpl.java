package in.hocg.eagle.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.DeleteStatus;
import in.hocg.eagle.basic.constant.datadict.FileRelType;
import in.hocg.eagle.modules.com.api.ro.UploadFileRo;
import in.hocg.eagle.modules.com.service.FileService;
import in.hocg.eagle.modules.pms.entity.Product;
import in.hocg.eagle.modules.pms.mapper.ProductMapper;
import in.hocg.eagle.modules.pms.mapstruct.ProductMapping;
import in.hocg.eagle.modules.pms.mapstruct.SkuMapping;
import in.hocg.eagle.modules.pms.pojo.qo.ProductCompleteQo;
import in.hocg.eagle.modules.pms.pojo.qo.ProductPagingQo;
import in.hocg.eagle.modules.pms.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.pms.pojo.vo.product.ProductComplexVo;
import in.hocg.eagle.modules.pms.service.ProductCategoryService;
import in.hocg.eagle.modules.pms.service.ProductService;
import in.hocg.eagle.modules.pms.service.SkuService;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.string.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * [Shop模块] 商品表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ProductServiceImpl extends AbstractServiceImpl<ProductMapper, Product>
    implements ProductService {
    private final ProductCategoryService productCategoryService;
    private final SkuService skuService;
    private final FileService fileService;
    private final ProductMapping mapping;
    private final SkuMapping skuMapping;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(ProductSaveQo qo) {
        Product entity = mapping.asProduct(qo);
        final Long userId = qo.getUserId();
        if (Objects.isNull(qo.getId())) {
            entity.setCreatedAt(qo.getCreatedAt());
            entity.setCreator(userId);
        } else {
            entity.setLastUpdatedAt(qo.getCreatedAt());
            entity.setLastUpdater(userId);
        }
        validInsertOrUpdate(entity);
        final Long productId = entity.getId();

        // 设置SKU
        final List<ProductSaveQo.Sku> allSku = qo.getSku();
        if (Objects.nonNull(allSku)) {
            skuService.validInsertOrUpdateByProductId(productId, allSku.parallelStream()
                .map(sku -> skuMapping.asSku(sku)
                    .setProductId(productId)
                    .setSpecData(JsonUtils.toJSONString(sku.getSpec())))
                .collect(Collectors.toList()));
        }

        // 设置图片
        final List<UploadFileRo.FileDto> photos = qo.getPhotos();
        if (Objects.nonNull(photos)) {
            fileService.upload(new UploadFileRo()
                .setRelType(FileRelType.Product.getCode())
                .setCreator(userId)
                .setRelId(productId)
                .setFiles(photos));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductComplexVo selectOne(Long id) {
        final Product entity = getById(id);
        ValidUtils.notNull(entity, "商品不存在");
        return this.convertComplex(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProductComplexVo> selectList(List<Long> ids) {
        final List<Product> entities = this.selectListById(ids);
        return entities.stream().map(this::convertComplex).collect(Collectors.toList());
    }

    @Override
    public IPage<ProductComplexVo> paging(ProductPagingQo qo) {
        IPage<Product> result = baseMapper.paging(qo, qo.page());
        return result.convert(this::convertComplex);
    }

    @Override
    public Product selectOneByIdAndNotDeleted(Long id) {
        return lambdaQuery().eq(Product::getId, id).eq(Product::getDeleteStatus, DeleteStatus.Off.getCode()).one();
    }

    private ProductComplexVo convertComplex(Product entity) {
        final Long productId = entity.getId();
        ProductComplexVo result = mapping.asProductComplex(entity);
        result.setPhotos(fileService.selectListByRelTypeAndRelId2(FileRelType.Product.getCode(), productId));
        result.setSku(skuService.selectListByProductId(productId));
        return result;
    }

    @Override
    public IPage<ProductComplexVo> pagingWithComplete(ProductCompleteQo qo) {
        return baseMapper.pagingWithComplete(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    public void validEntity(Product entity) {
        final Long productCategoryId = entity.getProductCategoryId();
        if (Objects.nonNull(productCategoryId)) {
            ValidUtils.notNull(productCategoryService.getById(productCategoryId), "商品品类错误");
        }
    }
}
