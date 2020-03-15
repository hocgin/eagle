package in.hocg.eagle.modules.oms.service.impl;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.SNCode;
import in.hocg.eagle.basic.constant.datadict.DeleteStatus;
import in.hocg.eagle.basic.constant.datadict.OrderConfirmStatus;
import in.hocg.eagle.basic.constant.datadict.OrderStatus;
import in.hocg.eagle.basic.constant.datadict.ProductPublishStatus;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.mapstruct.OrderMapping;
import in.hocg.eagle.mapstruct.SkuMapping;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.mapper.OrderMapper;
import in.hocg.eagle.modules.oms.pojo.qo.order.CalcOrderQo;
import in.hocg.eagle.modules.oms.pojo.qo.order.CancelOrderQo;
import in.hocg.eagle.modules.oms.pojo.qo.order.ConfirmOrderQo;
import in.hocg.eagle.modules.oms.pojo.qo.order.CreateOrderQo;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderService;
import in.hocg.eagle.modules.shop.entity.Product;
import in.hocg.eagle.modules.shop.entity.Sku;
import in.hocg.eagle.modules.shop.service.ProductService;
import in.hocg.eagle.modules.shop.service.SkuService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    private final SkuService skuService;
    private final SkuMapping skuMapping;
    private final OrderMapping mapping;
    private final SNCode snCode;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CalcOrderVo calcOrder(CalcOrderQo qo) {
        final CalcOrderVo result = new CalcOrderVo();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal payAmount = BigDecimal.ZERO;
        List<CalcOrderVo.OrderItem> orderItems = Lists.newArrayList();

        // 1. 检查商品状态
        BigDecimal remainingCouponAmount = BigDecimal.ZERO;
        final List<CalcOrderQo.Item> items = qo.getItems();
        for (int i = 0; i < items.size(); i++) {
            final CalcOrderQo.Item item = items.get(i);
            final Long skuId = item.getSkuId();
            final Sku sku = skuService.getById(skuId);
            ValidUtils.notNull(sku, "SKU不存在");
            Product product = productService.getById(sku.getProductId());
            ValidUtils.notNull(product, "商品不存在");
            ValidUtils.isFalse(LangUtils.equals(product.getDeleteStatus(), DeleteStatus.On.getCode()), "商品不存在");
            ValidUtils.isFalse(LangUtils.equals(product.getPublishStatus(), ProductPublishStatus.SoldOut.getCode()), "商品已下架");
            final Integer quantity = item.getQuantity();
            final BigDecimal price = sku.getPrice();
            ValidUtils.isTrue(quantity <= sku.getStock(), "超出数量范围");

            BigDecimal productTotalAmount = price.multiply(BigDecimal.valueOf(quantity));

            // 【开始】计算商品使用优惠券的金额
            BigDecimal couponAmount;
            if (i < (items.size() - 1)) {
                couponAmount = remainingCouponAmount.divide(BigDecimal.valueOf(items.size()), 2, RoundingMode.DOWN);
            } else {
                couponAmount = remainingCouponAmount;
            }
            remainingCouponAmount = remainingCouponAmount.subtract(couponAmount);
            // 【结束】计算商品使用优惠券的金额

            final BigDecimal realAmount = productTotalAmount.subtract(couponAmount);
            totalAmount = totalAmount.add(productTotalAmount);
            payAmount = payAmount.add(realAmount);
            orderItems.add(new CalcOrderVo.OrderItem()
                .setSkuCode(sku.getSkuCode())
                .setProductId(product.getId())
                .setTitle(product.getTitle())
                .setRealAmount(realAmount)
                .setCouponAmount(couponAmount)
                .setImageUrl(sku.getImageUrl())
                .setPrice(sku.getPrice())
                .setQuantity(quantity)
                .setSkuId(skuId)
                .setSpecData(sku.getSpecData()));
        }

        result.setOrderItems(orderItems);
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
            .setCouponId(qo.getCouponId())
            .setSourceType(qo.getSourceType())
            .setAccountId(currentUserId)
            .setOrderSn(snCode.getOrderSNCode())
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
            if (skuService.validAndUseStock(skuId, quantity)) {
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
        final Long id = qo.getId();
        final Order order = getById(id);
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), userId), "非订单所有人，操作失败");
        ValidUtils.notNull(order, "未找到订单");
        if (!Lists.newArrayList(OrderStatus.PendingPayment.getCode()).contains(order.getOrderStatus())) {
            throw ServiceException.wrap("取消失败，请联系客服");
        }

        final Order updated = new Order();
        updated.setId(id);
        updated.setOrderStatus(OrderStatus.Closed.getCode());
        updated.setLastUpdater(userId);
        updated.setLastUpdatedAt(qo.getCreatedAt());
        validUpdateById(updated);
    }

    public void payOrder(Long id) {
        // TODO
    }

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

    public void applyRefund() {
        // TODO
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderComplexVo selectOne(Long id) {
        final Order entity = getById(id);
        ValidUtils.notNull(entity, "未找到订单");
        return this.convertOrderComplex(entity);
    }

    private OrderComplexVo convertOrderComplex(Order entity) {
        final OrderComplexVo result = mapping.asOrderComplexVo(entity);
        result.setOrderItems(orderItemService.selectListByOrderId(entity.getId()));
        return result;
    }
}
