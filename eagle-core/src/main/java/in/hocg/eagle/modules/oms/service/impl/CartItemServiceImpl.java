package in.hocg.eagle.modules.oms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.constant.datadict.CartItemStatus;
import in.hocg.eagle.basic.constant.datadict.DeleteStatus;
import in.hocg.eagle.basic.constant.datadict.ProductPublishStatus;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.oms.entity.CartItem;
import in.hocg.eagle.modules.oms.mapper.CartItemMapper;
import in.hocg.eagle.modules.oms.mapstruct.CartItemMapping;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemPagingQo;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemSaveQo;
import in.hocg.eagle.modules.oms.pojo.vo.cart.CartItemComplexVo;
import in.hocg.eagle.modules.oms.service.CartItemService;
import in.hocg.eagle.modules.pms.api.ProductAPI;
import in.hocg.eagle.modules.pms.api.SkuAPI;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import in.hocg.eagle.modules.pms.api.vo.SkuComplexVo;
import in.hocg.eagle.modules.ums.api.AccountAPI;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * [订单模块] 购物车表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CartItemServiceImpl extends AbstractServiceImpl<CartItemMapper, CartItem> implements CartItemService {
    private final CartItemMapping mapping;
    private final AccountAPI accountService;
    private final SkuAPI skuService;
    private final ProductAPI productService;

    private Optional<CartItem> selectOneBySkuIdAndAccountId(Long skuId, Long accountId) {
        return this.lambdaQuery().eq(CartItem::getSkuId, skuId)
            .eq(CartItem::getAccountId, accountId).oneOpt();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(CartItemSaveQo qo) {
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();
        final Long skuId = qo.getSkuId();
        final Optional<CartItem> entityOpt = selectOneBySkuIdAndAccountId(skuId, userId);
        CartItem entity = new CartItem();
        entity.setQuantity(qo.getQuantity());
        if (entityOpt.isPresent()) {
            entity.setId(entityOpt.get().getId());
            entity.setLastUpdatedAt(createdAt);
            entity.setLastUpdater(userId);
        } else {
            final SkuComplexVo sku = skuService.selectOne(skuId);
            ValidUtils.notNull(sku, "商品规格错误");
            final ProductComplexVo product = productService.selectOne(sku.getProductId());
            ValidUtils.notNull(sku, "商品错误");
            entity.setSkuId(skuId);
            entity.setProductId(product.getId());
            entity.setAddProductTitle(product.getTitle());
            entity.setAddProductPrice(sku.getPrice());
            entity.setSkuSpecData(sku.getSpecData());
            entity.setAddProductImageUrl(sku.getImageUrl());
            entity.setAccountId(userId);
            entity.setCreatedAt(createdAt);
            entity.setCreator(userId);
        }
        validInsertOrUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMyCartItem(IdRo qo) {
        final Long id = qo.getId();
        final CartItem item = getById(id);
        ValidUtils.isTrue(LangUtils.equals(item.getAccountId(), qo.getUserId()), "操作失败");
        this.removeById(id);
    }

    @Override
    @ApiOperation("查询我的购物车 - 购物车")
    @Transactional(rollbackFor = Exception.class)
    public IPage<CartItemComplexVo> pagingMyCartItem(CartItemPagingQo qo) {
        qo.setAccountId(qo.getUserId());
        return this.paging(qo);
    }

    @Override
    @ApiOperation("新增我的购物车项 - 购物车")
    @Transactional(rollbackFor = Exception.class)
    public void insertMyCartItem(CartItemSaveQo qo) {
        this.saveOne(qo);

    }

    @Override
    @ApiOperation("更新我的购物车项 - 购物车")
    @Transactional(rollbackFor = Exception.class)
    public void updateMyCartItem(CartItemSaveQo qo) {
        this.saveOne(qo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<CartItemComplexVo> paging(CartItemPagingQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
    }

    private CartItemComplexVo convertComplex(CartItem entity) {
        CartItemComplexVo result = mapping.asCartItemComplexVo(entity);
        final Long skuId = result.getSkuId();
        final SkuComplexVo sku = skuService.selectOne(skuId);
        if (Objects.isNull(sku)) {
            result.setCartItemStatus(CartItemStatus.Expired.getCode());
            return result;
        }

        final ProductComplexVo product = productService.selectOne(sku.getProductId());
        if (Objects.isNull(product)
            || LangUtils.equals(product.getPublishStatus(), ProductPublishStatus.SoldOut.getCode())
            || LangUtils.equals(product.getDeleteStatus(), DeleteStatus.On.getCode())) {
            result.setCartItemStatus(CartItemStatus.Expired.getCode());
            return result;
        }

        if (!LangUtils.equals(sku.getSpecData(), entity.getSkuSpecData())) {
            result.setCartItemStatus(CartItemStatus.Expired.getCode());
            return result;
        }

        if (sku.getStock() < entity.getQuantity()) {
            result.setCartItemStatus(CartItemStatus.UnderStock.getCode());
            return result;
        }

        result.setCartItemStatus(CartItemStatus.Normal.getCode());
        return result;
    }

    @Override
    public void validEntity(CartItem entity) {
        final Long accountId = entity.getAccountId();
        if (Objects.nonNull(accountId)) {
            ValidUtils.notNull(accountService.selectOne(accountId), "账号错误");
        }

        final Long productId = entity.getProductId();
        if (Objects.nonNull(productId)) {
            ValidUtils.notNull(productService.selectOne(productId), "商品错误");
        }

        final Long skuId = entity.getSkuId();
        if (Objects.nonNull(skuId)) {
            ValidUtils.notNull(skuService.selectOne(skuId), "商品规格错误");
        }

    }
}
