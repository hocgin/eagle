package in.hocg.eagle.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.ProductMapping;
import in.hocg.eagle.mapstruct.SkuMapping;
import in.hocg.eagle.modules.base.entity.File;
import in.hocg.eagle.modules.base.pojo.qo.file.UploadFileDto;
import in.hocg.eagle.modules.base.service.FileService;
import in.hocg.eagle.modules.shop.entity.Product;
import in.hocg.eagle.modules.shop.mapper.ProductMapper;
import in.hocg.eagle.modules.shop.pojo.qo.ProductPagingQo;
import in.hocg.eagle.modules.shop.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.shop.pojo.vo.product.ProductComplexVo;
import in.hocg.eagle.modules.shop.service.ProductCategoryService;
import in.hocg.eagle.modules.shop.service.ProductService;
import in.hocg.eagle.modules.shop.service.SkuService;
import in.hocg.eagle.utils.JSONUtility;
import in.hocg.eagle.utils.ValidUtils;
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
public class ProductServiceImpl extends AbstractServiceImpl<ProductMapper, Product> implements ProductService {
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
                    .setSpecData(JSONUtility.toJSONString(sku.getSpec())))
                .collect(Collectors.toList()));
        }

        // 设置图片
        final List<UploadFileDto.FileDto> photos = qo.getPhotos();
        if (Objects.nonNull(photos)) {
            fileService.upload(new UploadFileDto()
                .setRelType(File.RelType.Product)
                .setCreator(userId)
                .setRelId(productId)
                .setFiles(photos));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductComplexVo selectOne(Long id) {
        return convertProductComplex(getById(id));
    }

    @Override
    public IPage<ProductComplexVo> paging(ProductPagingQo qo) {
        IPage<Product> result = baseMapper.paging(qo, qo.page());
        return result.convert(this::convertProductComplex);
    }

    public ProductComplexVo convertProductComplex(Product entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        final Long productId = entity.getId();
        ProductComplexVo result = mapping.asProductComplex(entity);
        result.setPhotos(fileService.selectListByRelTypeAndRelId2(File.RelType.Product, productId));
        result.setSku(skuService.selectListByProductId(productId));
        return result;
    }

    @Override
    public void validEntity(Product entity) {
        final Long productCategoryId = entity.getProductCategoryId();
        if (Objects.nonNull(productCategoryId)) {
            ValidUtils.notNull(productCategoryService.getById(productCategoryId), "商品品类错误");
        }
    }
}
