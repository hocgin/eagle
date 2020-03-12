package in.hocg.eagle.modules.shop.service.impl;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.ProductClassifyMapping;
import in.hocg.eagle.modules.base.entity.File;
import in.hocg.eagle.modules.base.pojo.qo.file.UploadFileDto;
import in.hocg.eagle.modules.base.service.FileService;
import in.hocg.eagle.modules.shop.entity.Product;
import in.hocg.eagle.modules.shop.entity.Sku;
import in.hocg.eagle.modules.shop.entity.Spec;
import in.hocg.eagle.modules.shop.entity.SpecValue;
import in.hocg.eagle.modules.shop.mapper.ProductMapper;
import in.hocg.eagle.modules.shop.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.shop.pojo.vo.product.ProductComplexVo;
import in.hocg.eagle.modules.shop.service.*;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    private final ProductClassifyService productClassifyService;
    private final SpecService specService;
    private final SpecValueService specValueService;
    private final SkuService skuService;
    private final FileService fileService;
    private final ProductClassifyMapping mapping;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(ProductSaveQo qo) {
        Product entity = mapping.asProduct(qo);
        final Long userId = qo.getUserId();
        if (Objects.nonNull(qo.getId())) {
            entity.setCreatedAt(qo.getCreatedAt());
            entity.setCreator(userId);
        } else {
            entity.setLastUpdatedAt(qo.getCreatedAt());
            entity.setLastUpdater(userId);
        }
        validInsertOrUpdate(entity);
        final Long productId = entity.getId();

        // 设置规格
        final List<String> specList = qo.getSpec();
        final List<ProductSaveQo.Sku> skuList = qo.getSku();

        // 设置规格 && 设置SKU
        if (Objects.nonNull(specList) && Objects.nonNull(skuList)) {
            ValidUtils.isTrue(Math.pow(specList.size(), 2D) == skuList.size(), "规格数量不一致");
            specService.deleteAllByProductId(productId);

            // 新增规格键
            List<Long> specIds = Lists.newArrayList();
            for (String spec : specList) {
                final Spec specEntity = new Spec()
                    .setProductId(productId)
                    .setTitle(spec);
                specService.validInsert(specEntity);
                specIds.add(specEntity.getId());
            }

            for (final ProductSaveQo.Sku sku : skuList) {
                final List<String> specValues = sku.getSpec();
                ValidUtils.isTrue(specList.size() == specValues.size(), "规格值错误");
                final Sku skuEntity = new Sku()
                    .setSku(sku.getSku())
                    .setPrice(sku.getPrice())
                    .setInventory(sku.getInventory())
                    .setImageUrl(sku.getImageUrl());

                // 新增SKU
                skuService.validInsert(skuEntity);
                final Long skuId = skuEntity.getId();

                // 新增规格值
                for (int i1 = 0; i1 < specValues.size(); i1++) {
                    final Long specId = specIds.get(i1);
                    final String specValue = specValues.get(i1);
                    specValueService.validInsert(new SpecValue()
                        .setSkuId(skuId)
                        .setSpecId(specId)
                        .setValue(specValue));
                }
            }
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

    public ProductComplexVo convertProductComplex(Product entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        final Long productId = entity.getId();
        ProductComplexVo result = mapping.asProductComplex(entity);
        result.setPhotos(fileService.selectListByRelTypeAndRelId2(File.RelType.Product, productId));
        result.setSku(skuService.selectListByProductId(productId));
        result.setSpec(specService.selectListByProductId(productId));
        return result;
    }

    @Override
    public void validEntity(Product entity) {
        final Long classifyId = entity.getClassifyId();
        if (Objects.nonNull(classifyId)) {
            ValidUtils.notNull(productClassifyService.getById(classifyId), "商品分类错误");
        }
    }
}
