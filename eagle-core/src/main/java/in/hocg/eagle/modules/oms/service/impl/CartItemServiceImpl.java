package in.hocg.eagle.modules.oms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.CartItemStatus;
import in.hocg.eagle.basic.constant.datadict.DeleteStatus;
import in.hocg.eagle.basic.constant.datadict.ProductPublishStatus;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.mapstruct.CartItemMapping;
import in.hocg.eagle.modules.oms.entity.CartItem;
import in.hocg.eagle.modules.oms.mapper.CartItemMapper;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemPagingQo;
import in.hocg.eagle.modules.oms.pojo.qo.cart.CartItemSaveQo;
import in.hocg.eagle.modules.oms.pojo.vo.cart.CartItemComplexVo;
import in.hocg.eagle.modules.oms.service.CartItemService;
import in.hocg.eagle.modules.pms.entity.Product;
import in.hocg.eagle.modules.pms.entity.Sku;
import in.hocg.eagle.modules.pms.service.ProductService;
import in.hocg.eagle.modules.pms.service.SkuService;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
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
    private final AccountService accountService;
    private final SkuService skuService;
    private final ProductService productService;

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
            final Sku sku = skuService.getById(skuId);
            ValidUtils.notNull(sku, "商品规格错误");
            final Product product = productService.getById(skuId);
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
    public void deleteOne(IdQo qo) {
        final Long id = qo.getId();
        this.removeById(id);
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
        final Sku sku = skuService.getById(skuId);
        if (Objects.isNull(sku)) {
            result.setCartItemStatus(CartItemStatus.Expired.getCode());
            return result;
        }

        final Product product = productService.getById(sku.getProductId());
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
            ValidUtils.notNull(accountService.getById(accountId), "账号错误");
        }

        final Long productId = entity.getProductId();
        if (Objects.nonNull(productId)) {
            ValidUtils.notNull(productService.getById(productId), "商品错误");
        }

        final Long skuId = entity.getSkuId();
        if (Objects.nonNull(skuId)) {
            ValidUtils.notNull(productService.getById(skuId), "商品规格错误");
        }

    }
}
