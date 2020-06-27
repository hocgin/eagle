package in.hocg.eagle.modules.pms.service.impl;

import com.alibaba.fastjson.JSON;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.pms.entity.Sku;
import in.hocg.eagle.modules.pms.mapper.SkuMapper;
import in.hocg.eagle.modules.pms.mapstruct.SkuMapping;
import in.hocg.eagle.modules.pms.pojo.vo.sku.SkuComplexVo;
import in.hocg.eagle.modules.pms.service.ProductService;
import in.hocg.eagle.modules.pms.service.SkuService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
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
    private final ProductService productService;

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
        final List<Sku> allData = this.selectListByProductId2(productId);

        final BiFunction<Sku, Sku, Boolean> isSame =
            (t1, t2) -> LangUtils.equals(t1.getId(), t2.getId());
        final List<Sku> mixedList = LangUtils.getMixed(allData, entities, isSame);
        List<Sku> deleteList = LangUtils.removeIfExits(allData, mixedList, isSame);
        List<Sku> addList = LangUtils.removeIfExits(entities, mixedList, isSame);

        // 删除
        final List<Long> deleteIds = deleteList.parallelStream().map(Sku::getId)
            .collect(Collectors.toList());
        this.removeByIds(deleteIds);

        // 新增
        addList.forEach(this::validInsertOrUpdate);

        // 更新
        mixedList.forEach(this::validInsertOrUpdate);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 1, rollbackFor = Exception.class)
    public boolean casValidAndSubtractStock(Long skuId, Integer useStock) {
        final Sku sku = baseMapper.selectById(skuId);
        final Integer stock = sku.getStock();
        if (stock < useStock) {
            return false;
        }
        final boolean isOk = this.retBool(baseMapper.subtractStock(skuId, useStock, stock));
        if (isOk) {
            return true;
        }
        return ((SkuService) AopContext.currentProxy()).casValidAndSubtractStock(skuId, useStock);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 1, rollbackFor = Exception.class)
    public boolean casValidAndPlusStock(Long skuId, Integer useStock) {
        final Sku sku = baseMapper.selectById(skuId);
        final boolean isOk = this.retBool(baseMapper.plusStock(skuId, useStock, sku.getStock()));
        if (isOk) {
            return true;
        }
        return ((SkuService) AopContext.currentProxy()).casValidAndPlusStock(skuId, useStock);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Sku selectOneBySkuIdAndValidQuantity(Long skuId, Integer quantity) {
        final Sku sku = getById(skuId);
        ValidUtils.notNull(sku, "SKU不存在");
        ValidUtils.isTrue(quantity <= sku.getStock(), "超出数量范围");
        return sku;
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

    @Override
    public void validEntity(Sku entity) {
        final Long productId = entity.getProductId();
        if (Objects.nonNull(productId)) {
            ValidUtils.notNull(productService.getById(productId), "商品ID不能为空");
        }
    }
}
