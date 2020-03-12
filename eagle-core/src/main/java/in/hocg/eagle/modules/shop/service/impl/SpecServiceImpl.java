package in.hocg.eagle.modules.shop.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.SpecMapping;
import in.hocg.eagle.modules.shop.entity.Spec;
import in.hocg.eagle.modules.shop.mapper.SpecMapper;
import in.hocg.eagle.modules.shop.pojo.vo.spec.SpecComplexVo;
import in.hocg.eagle.modules.shop.service.SkuService;
import in.hocg.eagle.modules.shop.service.SpecService;
import in.hocg.eagle.modules.shop.service.SpecValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * [Shop模块] 商品规格表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SpecServiceImpl extends AbstractServiceImpl<SpecMapper, Spec> implements SpecService {

    private final SpecMapping mapping;
    private final SpecValueService specValueService;
    private final SkuService skuService;

    @Override
    public void deleteAllByProductId(Long productId) {
        skuService.deleteAllByProductId(productId);
        specValueService.deleteAllByProductId(productId);
        baseMapper.deleteAllByProductId(productId);
    }

    @Override
    public List<SpecComplexVo> selectListByProductId(Long productId) {
        return selectListByProductId2(productId).stream()
            .map(this::convertSpecComplex)
            .collect(Collectors.toList());
    }

    private SpecComplexVo convertSpecComplex(Spec entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        final Long id = entity.getId();
        final SpecComplexVo result = mapping.asSpecComplexVo(entity);
        result.setValues(specValueService.selectListBySpecId(id));
        return result;
    }

    public List<Spec> selectListByProductId2(Long productId) {
        return lambdaQuery().eq(Spec::getProductId, productId).list();
    }
}
