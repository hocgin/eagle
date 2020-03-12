package in.hocg.eagle.modules.shop.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.SkuMapping;
import in.hocg.eagle.modules.shop.entity.Sku;
import in.hocg.eagle.modules.shop.entity.SpecValue;
import in.hocg.eagle.modules.shop.mapper.SkuMapper;
import in.hocg.eagle.modules.shop.pojo.vo.sku.SkuComplexVo;
import in.hocg.eagle.modules.shop.service.SkuService;
import in.hocg.eagle.modules.shop.service.SpecValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * [Shop模块] 商品SKU表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SkuServiceImpl extends AbstractServiceImpl<SkuMapper, Sku> implements SkuService {

    private final SkuMapping mapping;
    private final SpecValueService specValueService;

    @Override
    public void deleteAllByProductId(Long productId) {
        baseMapper.deleteAllByProductId(productId);
    }

    @Override
    public List<SkuComplexVo> selectListByProductId(Long productId) {
        final List<Sku> entities = selectListByProductId2(productId);
        return entities.stream().map(this::convertSkuComplex)
            .collect(Collectors.toList());
    }

    private SkuComplexVo convertSkuComplex(Sku entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        final Long id = entity.getId();
        List<SpecValue> specValues = specValueService.selectListBySkuId(id);
        final List<Long> specValueId = specValues.parallelStream().map(SpecValue::getId).collect(Collectors.toList());
        final List<String> specValue = specValues.parallelStream().map(SpecValue::getValue).collect(Collectors.toList());

        SkuComplexVo result = mapping.asSkuComplexVo(entity);
        result.setSpecValue(specValue);
        result.setSpecValueId(specValueId);
        return result;
    }

    private List<Sku> selectListByProductId2(Long productId) {
        return baseMapper.selectListByProductId2(productId);
    }
}
