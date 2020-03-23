package in.hocg.eagle.modules.oms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.SNCode;
import in.hocg.eagle.basic.constant.datadict.*;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.mapstruct.OrderMapping;
import in.hocg.eagle.mapstruct.OrderReturnApplyMapping;
import in.hocg.eagle.mapstruct.SkuMapping;
import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.entity.OrderReturnApply;
import in.hocg.eagle.modules.oms.helper.order.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order.discount.DiscountHelper;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.Coupon;
import in.hocg.eagle.modules.oms.mapper.OrderMapper;
import in.hocg.eagle.modules.oms.pojo.dto.order.OrderItemDto;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.coupon.CouponAccountComplexVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderReturnApplyService;
import in.hocg.eagle.modules.oms.service.OrderService;
import in.hocg.eagle.modules.pms.entity.Product;
import in.hocg.eagle.modules.pms.entity.Sku;
import in.hocg.eagle.modules.pms.service.ProductService;
import in.hocg.eagle.modules.pms.service.SkuService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * [订单模块] 订单主表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderServiceImpl extends AbstractServiceImpl<OrderMapper, Order> implements OrderService {
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final CouponService couponService;
    private final CouponAccountService couponAccountService;
    private final OrderReturnApplyService orderReturnApplyService;
    private final SkuService skuService;
    private final SkuMapping skuMapping;
    private final OrderReturnApplyMapping orderReturnApplyMapping;
    private final OrderMapping mapping;
    private final SNCode snCode;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CalcOrderVo calcOrder(CalcOrderQo qo) {
        final CalcOrderVo result = new CalcOrderVo();

        // 1. 检查商品 和 检查库存
        final List<GeneralProduct> products = qo.getItems().stream()
            .map(item -> {
                final Sku sku = skuService.getById(item.getSkuId());
                ValidUtils.notNull(sku, "商品规格错误");
                final Long productId = sku.getProductId();
                final Product product = productService.selectOneByIdAndNotDeleted(productId);
                ValidUtils.notNull(product, "未找到商品");
                ValidUtils.isFalse(LangUtils.equals(product.getPublishStatus(), ProductPublishStatus.SoldOut.getCode()), "商品已下架");
                return new GeneralProduct(sku.getPrice(), item.getQuantity())
                    .setProductSpecData(sku.getSpecData())
                    .setProductCategoryId(product.getProductCategoryId())
                    .setProductPic(sku.getImageUrl())
                    .setProductSkuId(sku.getId())
                    .setProductSkuCode(sku.getSkuCode())
                    .setProductName(product.getTitle())
                    .setProductId(product.getId());
            })
            .collect(Collectors.toList());

        @NonNull final Long accountId = qo.getUserId();
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userCouponId = qo.getUserCouponId();
        final GeneralOrder generalOrder = new GeneralOrder(products, accountId, OrderSourceType.APP, createdAt);

        if (Objects.nonNull(userCouponId)) {
            CouponAccountComplexVo couponVo = couponAccountService.selectOne(userCouponId);
            ValidUtils.isTrue(LangUtils.equals(couponVo.getAccountId(), accountId), "无法使用该优惠券");
            Coupon coupon = DiscountHelper.createCoupon(couponVo);
            generalOrder.use(coupon);
            result.setCoupon(new CalcOrderVo.Coupon()
                .setId((Long) coupon.id())
                .setTitle(coupon.title())
                .setCouponAmount(coupon.getDiscountTotalAmount()));
        }
        final BigDecimal totalAmount = generalOrder.getPreDiscountTotalAmount();
        final BigDecimal payAmount = generalOrder.getPreferentialAmount();

        List<OrderItemDto> orderItems = generalOrder.mapProduct(item -> new OrderItemDto()
            .setProductSpecData(item.getProductSpecData())
            .setProductCategoryId(item.getProductCategoryId())
            .setProductPic(item.getProductPic())
            .setProductSkuId(item.getProductSkuId())
            .setProductSkuCode(item.getProductSkuCode())
            .setProductQuantity(item.getProductQuantity())
            .setProductPrice(item.getProductPrice())
            .setProductName(item.getProductName())
            .setProductId(item.getProductId())
            .setCouponAmount(item.getDiscountPrice())
            .setRealAmount(item.getPreferentialPrice()));

        result.setOrderItems(orderItems.parallelStream()
            .map(mapping::asCalcOrderVoOrderItem)
            .collect(Collectors.toList()));
        result.setTotalAmount(totalAmount);
        result.setPayAmount(payAmount);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(CreateOrderQo qo) {
        final Long currentUserId = qo.getUserId();
        final CalcOrderVo calcResult = calcOrder(qo);
        final CalcOrderVo.Coupon coupon = calcResult.getCoupon();

        final CreateOrderQo.Receiver receiver = qo.getReceiver();
        final Order order = new Order()
            .setOrderSn(snCode.getOrderSNCode())
            .setCouponAccountId(qo.getUserCouponId())
            .setSourceType(qo.getSourceType())
            .setAccountId(currentUserId)
            .setFreightAmount(BigDecimal.ZERO)
            .setTotalAmount(calcResult.getTotalAmount())
            .setPayAmount(calcResult.getPayAmount())
            .setOrderStatus(OrderStatus.PendingPayment.getCode())
            .setAutoConfirmDay(15)
            .setRemark(qo.getRemark())
            // 收货人信息
            .setReceiverName(receiver.getName())
            .setReceiverPhone(receiver.getPhone())
            .setReceiverPostCode(receiver.getPostCode())
            .setReceiverProvince(receiver.getProvince())
            .setReceiverCity(receiver.getCity())
            .setReceiverRegion(receiver.getRegion())
            .setReceiverDetailAddress(receiver.getDetailAddress())
            // 创建信息
            .setCreatedAt(qo.getCreatedAt())
            .setCreator(currentUserId);

        // 如果使用了优惠券
        if (Objects.nonNull(coupon)) {
            final Long userCouponId = coupon.getId();
            order.setCouponAccountId(userCouponId);
            order.setCouponAmount(coupon.getCouponAmount());
            final BigDecimal couponAmount = coupon.getCouponAmount();
            if (!couponAccountService.updateUsedStatus(userCouponId, couponAmount)) {
                throw ServiceException.wrap("优惠券已被使用");
            }
        }
        validInsert(order);
        final Long orderId = order.getId();

        final List<CalcOrderVo.OrderItem> orderItems = calcResult.getOrderItems();
        final List<OrderItem> orderItemList = orderItems.parallelStream().map(item -> new OrderItem()
            .setRealAmount(item.getRealAmount())
            .setProductSpecData(item.getSpecData())
            .setProductSkuCode(item.getSkuCode())
            .setCouponAmount(item.getCouponAmount())
            .setProductName(item.getTitle())
            .setProductQuantity(item.getQuantity())
            .setProductPic(item.getImageUrl())
            .setProductPrice(item.getPrice())
            .setProductId(item.getProductId())
            .setProductSkuId(item.getSkuId())
            .setOrderId(orderId)).collect(Collectors.toList());
        for (OrderItem orderItem : orderItemList) {
            final Long skuId = orderItem.getProductSkuId();
            final Integer quantity = orderItem.getProductQuantity();
            if (skuService.casValidAndSubtractStock(skuId, quantity)) {
                orderItemService.validInsert(orderItem);
            } else {
                throw ServiceException.wrap("库存商品不足");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(CancelOrderQo qo) {
        final Long userId = qo.getUserId();
        final Long orderId = qo.getId();
        final Order order = getById(orderId);
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), userId), "非订单所有人，操作失败");
        ValidUtils.notNull(order, "未找到订单");
        if (!Lists.newArrayList(OrderStatus.PendingPayment.getCode()).contains(order.getOrderStatus())) {
            throw ServiceException.wrap("取消失败，请联系客服");
        }

        final Order updated = new Order();
        updated.setId(orderId);
        updated.setOrderStatus(OrderStatus.Closed.getCode());
        updated.setLastUpdater(userId);
        updated.setLastUpdatedAt(qo.getCreatedAt());
        validUpdateById(updated);
        this.handleCancelOrClosedOrderAfter(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(PayOrderQo qo) {
        // TODO
        //  支付成功后 生成 .setOrderSn(snCode.getOrderSNCode())
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmOrder(ConfirmOrderQo qo) {
        final Long userId = qo.getUserId();
        final Long id = qo.getId();
        final Order order = getById(id);
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), userId), "非订单所有人，操作失败");

        ValidUtils.notNull(order, "未找到订单");
        if (!Lists.newArrayList(OrderStatus.Shipped.getCode()).contains(order.getOrderStatus())) {
            throw ServiceException.wrap("确认失败，请联系客服");
        }

        final LocalDateTime createdAt = qo.getCreatedAt();
        final Order updated = new Order();
        updated.setId(id);
        updated.setOrderStatus(OrderStatus.Completed.getCode());
        updated.setConfirmStatus(ConfirmStatus.Confirmed.getCode());
        updated.setReceiveTime(createdAt);
        updated.setLastUpdater(userId);
        updated.setLastUpdatedAt(createdAt);
        validUpdateById(updated);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyRefund(RefundApplyQo qo) {
        final Long userId = qo.getUserId();
        final Long orderItemId = qo.getId();
        final OrderItem orderItem = orderItemService.getById(orderItemId);
        ValidUtils.notNull(orderItem, "订单商品不存在");
        final Long orderId = orderItem.getOrderId();
        final Order order = getById(orderId);
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), userId), "非订单所有人，操作失败");
        final Optional<OrderReturnApply> orderReturnApplyOpt = orderReturnApplyService.selectOneByOrderItemId(orderItemId);
        if (!orderReturnApplyOpt.isPresent()) {
            OrderReturnApply apply = orderReturnApplyMapping.asOrderReturnApply(qo, orderItem);
            apply.setApplySn(snCode.getOrderReturnApplySNCode());
            orderReturnApplyService.validInsert(apply);
        } else {
            final OrderReturnApply apply = orderReturnApplyOpt.get();
            final Optional<OrderReturnApplyStatus> applyStatusOpt = IntEnum.of(apply.getApplyStatus(), OrderReturnApplyStatus.class);
            if (applyStatusOpt.isPresent()) {
                final OrderReturnApplyStatus applyStatus = applyStatusOpt.get();
                throw ServiceException.wrap("已进行退款申请，申请状态为" + applyStatus.getName());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderComplexVo selectOne(Long id) {
        final Order entity = getById(id);
        ValidUtils.notNull(entity, "未找到订单");
        return this.convertOrderComplex(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<OrderComplexVo> paging(OrderPagingQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertOrderComplex);
    }

    private OrderComplexVo convertOrderComplex(Order entity) {
        final OrderComplexVo result = mapping.asOrderComplexVo(entity);
        result.setOrderItems(orderItemService.selectListByOrderId(entity.getId()));
        return result;
    }

    /**
     * 处理关闭订单或取消订单之后的逻辑
     * - 库存归还
     * - 优惠券状态变更
     *
     * @param orderId
     */
    private void handleCancelOrClosedOrderAfter(@NonNull Long orderId) {
        final Order order = baseMapper.selectById(orderId);
        ValidUtils.notNull(order, "订单不存在");
        // 取消订单锁定库存
        final List<OrderItem> orderItems = orderItemService.selectListByOrderId2(orderId);
        for (OrderItem orderItem : orderItems) {
            final Long skuId = orderItem.getProductSkuId();
            final Integer quantity = orderItem.getProductQuantity();
            if (!skuService.casValidAndPlusStock(skuId, quantity)) {
                throw ServiceException.wrap("系统繁忙，请稍后");
            }
        }

        // 归还优惠券
        final Long accountCouponId = order.getCouponAccountId();
        if (Objects.nonNull(accountCouponId)) {
            final CouponAccount updated = new CouponAccount();
            updated.setId(accountCouponId);
            updated.setUseStatus(CouponUseStatus.Unused.getCode());
            couponAccountService.validUpdateById(updated);
        }
    }
}
