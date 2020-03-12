package in.hocg.eagle.modules.shop.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.SpecValueMapping;
import in.hocg.eagle.modules.shop.entity.SpecValue;
import in.hocg.eagle.modules.shop.mapper.SpecValueMapper;
import in.hocg.eagle.modules.shop.pojo.vo.spec.SpecValueComplexVo;
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
 * [Shop模块] 商品规格值表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SpecValueServiceImpl extends AbstractServiceImpl<SpecValueMapper, SpecValue> implements SpecValueService {
    private final SkuService skuService;
    private final SpecValueMapping specValueMapping;

    @Override
    public void deleteAllByProductId(Long productId) {
        skuService.deleteAllByProductId(productId);
        baseMapper.deleteAllByProductId(productId);
    }

    @Override
    public List<SpecValue> selectListBySkuId(Long skuId) {
        return lambdaQuery().eq(SpecValue::getSkuId, skuId).list();
    }

    @Override
    public List<SpecValueComplexVo> selectListBySpecId(Long specId) {
        return selectListBySpecId2(specId).stream().map(this::convertSpecValueComplex).collect(Collectors.toList());
    }

    private SpecValueComplexVo convertSpecValueComplex(SpecValue entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return specValueMapping.asSpecValueComplex(entity);
    }

    private List<SpecValue> selectListBySpecId2(Long specId) {
        return lambdaQuery().eq(SpecValue::getSpecId, specId).list();
    }
}
