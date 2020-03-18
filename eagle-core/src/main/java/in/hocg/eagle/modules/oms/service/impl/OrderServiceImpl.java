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
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.entity.OrderReturnApply;
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
import java.math.RoundingMode;
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
        final List<OrderItemDto> orderItems = qo.getItems().stream()
            .map(item -> {
                final Sku sku = skuService.getById(item.getSkuId());
                ValidUtils.notNull(sku, "商品规格错误");
                final Long productId = sku.getProductId();
                final Product product = productService.selectOneByIdAndNotDeleted(productId);
                ValidUtils.notNull(product, "未找到商品");
                ValidUtils.isFalse(LangUtils.equals(product.getPublishStatus(), ProductPublishStatus.SoldOut.getCode()), "商品已下架");
                OrderItemDto result1 = skuMapping.asOrderItemDto(sku, product);
                result1.setProductQuantity(item.getQuantity());
                return result1;
            })
            .collect(Collectors.toList());

        // 2. 计算优惠券优惠金额
        final Long userCouponId = qo.getUserCouponId();
        Optional<CouponAccountComplexVo> couponOpt = getAndValidCoupon(orderItems, userCouponId, CouponPlatformType.PC, qo.getCreatedAt());
        couponOpt.ifPresent(couponAccountComplexVo -> handleCouponAmount(orderItems, couponAccountComplexVo));

        // 3. 确定价格
        handleRealAmount(orderItems);

        // 4. 获取需要支付的金额
        BigDecimal payAmount = getPayAmount(orderItems);
        final BigDecimal totalAmount = getItemsTotalAmount(orderItems);

        result.setOrderItems(orderItems.parallelStream()
            .map(mapping::asCalcOrderVoOrderItem)
            .collect(Collectors.toList()));
        result.setTotalAmount(totalAmount);
        result.setPayAmount(payAmount);
        return result;
    }

    /**
     * 统计总支付金额
     *
     * @param items
     * @return
     */
    private BigDecimal getPayAmount(List<OrderItemDto> items) {
        return items.parallelStream()
            .map(OrderItemDto::getRealAmount)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    /**
     * 计算每个项目需要支付的金额
     *
     * @param items
     */
    private void handleRealAmount(List<OrderItemDto> items) {
        for (OrderItemDto item : items) {
            final BigDecimal realAmount = item.getProductPrice()
                .multiply(new BigDecimal(item.getProductQuantity()))
                .subtract(item.getCouponAmount());
            item.setRealAmount(realAmount);
        }
    }

    private Optional<CouponAccountComplexVo> getAndValidCoupon(@NonNull List<OrderItemDto> items,
                                                               Long userCouponId,
                                                               CouponPlatformType platformType,
                                                               @NonNull LocalDateTime now) {
        CouponAccountComplexVo coupon = couponAccountService.selectOne(userCouponId);
        if (Objects.isNull(coupon) || Objects.isNull(platformType)) {
            return Optional.empty();
        }
        if (!LangUtils.equals(CouponUseStatus.Unused.getCode(), coupon.getUseStatus())) {
            return Optional.empty();
        }
        final LocalDateTime startAt = coupon.getStartAt();
        final LocalDateTime endAt = coupon.getEndAt();
        if (!(now.isBefore(endAt) && now.isAfter(startAt))) {
            return Optional.empty();
        }

        if (!LangUtils.equals(platformType.getCode(), coupon.getPlatform())) {
            return Optional.empty();
        }

        final List<OrderItemDto> eligibleProducts = items.parallelStream()
            .filter(dto -> LangUtils.equals(coupon.getUseType(), CouponUseType.Universal.getCode())
                || coupon.getCanUseProductId().contains(dto.getProductId())
                || coupon.getCanUseProductCategoryId().contains(dto.getProductCategoryId()))
            .collect(Collectors.toList());

        if (eligibleProducts.isEmpty()) {
            return Optional.empty();
        }
        final BigDecimal totalAmount = getItemsTotalAmount(eligibleProducts);
        if (totalAmount.compareTo(coupon.getMinPoint()) < 0) {
            return Optional.empty();
        }
        return Optional.of(coupon);
    }

    /**
     * 处理优惠券
     *  @param items
     * @param coupon 优惠券
     */
    private void handleCouponAmount(List<OrderItemDto> items,
                                    CouponAccountComplexVo coupon) {
        final Integer couponType = coupon.getCouponType();
        BigDecimal couponTotalAmount = BigDecimal.ZERO;
        // = 所有商品的总价
        BigDecimal totalAmount = getItemsTotalAmount(items);
        if (LangUtils.equals(CouponType.Credit.getCode(), couponType)) {
            couponTotalAmount = coupon.getCredit();
        } else if (LangUtils.equals(CouponType.Discount.getCode(), couponType)) {
//            couponTotalAmount = coupon.getCeiling().subtract(coupon.get);
        } else {
            throw ServiceException.wrap("优惠券类型错误");
        }

        // 2. 计算优惠金额
        // = 优惠券优惠总金额
        // = 优惠券剩余可优惠金额
        BigDecimal remainingCouponAmount = couponTotalAmount;

        // 3. 进行优惠
        BigDecimal couponAmount;
        for (int i = 0; i < items.size(); i++) {
            final OrderItemDto item = items.get(i);
            if (i < (items.size() - 1)) {
                // = 商品总价
                final BigDecimal productTotalAmount = item.getProductPrice().multiply(item.getProductPrice());
                // = 优惠价格
                couponAmount = couponTotalAmount.multiply(productTotalAmount).divide(totalAmount, 2, RoundingMode.DOWN);
            } else {
                couponAmount = remainingCouponAmount;
            }
            remainingCouponAmount = remainingCouponAmount.subtract(couponAmount);
            item.setCouponAmount(couponAmount);
        }
    }

    /**
     * 获取商品总价
     *
     * @param orderItems
     * @return
     */
    private BigDecimal getItemsTotalAmount(List<OrderItemDto> orderItems) {
        return orderItems.parallelStream().map(dto -> dto.getProductPrice()
            .multiply(new BigDecimal(dto.getProductQuantity())))
            .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(CreateOrderQo qo) {
        final Long currentUserId = qo.getUserId();
        final CalcOrderVo calcResult = calcOrder(qo);
        final CalcOrderVo.Coupon coupon = calcResult.getCoupon();

        final CreateOrderQo.Receiver receiver = qo.getReceiver();
        final Order order = new Order()
            .setCouponId(qo.getUserCouponId())
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
            order.setCouponId(coupon.getId());
            order.setCouponAmount(coupon.getCouponAmount());
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
        updated.setConfirmStatus(OrderConfirmStatus.Confirmed.getCode());
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
    }
}
