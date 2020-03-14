package in.hocg.eagle.modules.shop.service.impl;

import com.alibaba.fastjson.JSON;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.SkuMapping;
import in.hocg.eagle.modules.shop.entity.Sku;
import in.hocg.eagle.modules.shop.mapper.SkuMapper;
import in.hocg.eagle.modules.shop.pojo.vo.sku.SkuComplexVo;
import in.hocg.eagle.modules.shop.service.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void validInsertOrUpdateByProductId(@NotNull Long productId, List<Sku> entities) {
        final List<Long> updateIds = entities.parallelStream().filter(sku -> Objects.nonNull(sku.getId())).map(Sku::getId).collect(Collectors.toList());
        final List<Long> deleteIds = selectListByProductId2(productId).parallelStream().map(Sku::getId).filter(id -> !updateIds.contains(id)).collect(Collectors.toList());
        this.removeByIds(deleteIds);
        entities.forEach(this::validInsertOrUpdate);
    }

    private SkuComplexVo convertSkuComplex(Sku entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        SkuComplexVo result = mapping.asSkuComplexVo(entity);
        result.setSpec(JSON.parseArray(entity.getSpecData(), SkuComplexVo.Spec.class));
        return result;
    }

    private List<Sku> selectListByProductId2(Long productId) {
        return baseMapper.selectListByProductId2(productId);
    }
}
