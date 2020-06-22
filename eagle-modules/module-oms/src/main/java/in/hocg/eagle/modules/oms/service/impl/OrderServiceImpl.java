package in.hocg.eagle.modules.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.web.AbstractServiceImpl;
import in.hocg.web.env.Env;
import in.hocg.web.env.EnvConfigs;
import in.hocg.web.constant.datadict.*;
import in.hocg.web.exception.ServiceException;
import in.hocg.web.lang.SNCode;
import in.hocg.web.pojo.KeyValue;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.eagle.modules.bmw.api.PaymentAPI;
import in.hocg.eagle.modules.bmw.pojo.ro.CreateTradeRo;
import in.hocg.eagle.modules.bmw.pojo.ro.GoPayRo;
import in.hocg.eagle.modules.bmw.pojo.vo.GoPayVo;
import in.hocg.eagle.modules.com.service.ChangeLogService;
import in.hocg.eagle.modules.mkt.entity.CouponAccount;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import in.hocg.eagle.modules.mkt.service.CouponService;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.helper.order.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order.discount.DiscountHelper;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.Coupon;
import in.hocg.eagle.modules.oms.mapper.OrderMapper;
import in.hocg.eagle.modules.oms.mapstruct.OrderMapping;
import in.hocg.eagle.modules.oms.pojo.dto.order.OrderItemDto;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderService;
import in.hocg.eagle.modules.pms.entity.Product;
import in.hocg.eagle.modules.pms.entity.Sku;
import in.hocg.eagle.modules.pms.mapstruct.SkuMapping;
import in.hocg.eagle.modules.pms.service.ProductService;
import in.hocg.eagle.modules.pms.service.SkuService;
import in.hocg.web.utils.LangUtils;
import in.hocg.web.utils.ValidUtils;
import in.hocg.web.utils.compare.EntityCompare;
import in.hocg.web.utils.compare.FieldChangeDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
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
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderServiceImpl extends AbstractServiceImpl<OrderMapper, Order>
    implements OrderService {
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final CouponService couponService;
    private final PaymentAPI paymentApi;
    private final CouponAccountService couponAccountService;
    private final SkuService skuService;
    private final SkuMapping skuMapping;
    private final OrderMapping mapping;
    private final ChangeLogService changeLogService;
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
            .map((item) -> {
                final String specData = item.getProductSpecData();
                return mapping.asCalcOrderVoOrderItem(item)
                    .setSpec(JSON.parseArray(specData, KeyValue.class));
            })
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

        // 生成交易
        final EnvConfigs configs = Env.getConfigs();
        final Long paymentAppSn = configs.getPaymentAppSn();
        final String orderSn = order.getOrderSn();
        final BigDecimal totalAmount = order.getTotalAmount();
        String notifyUrl = String.format("%s/api/order/async", configs.getHostname());
        final String tradeSn = paymentApi.createTrade(new CreateTradeRo(paymentAppSn, orderSn, totalAmount).setNotifyUrl(notifyUrl));
        this.updateById(new Order().setId(orderId).setTradeSn(tradeSn));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(CancelOrderQo qo) {
        final Long userId = qo.getUserId();
        final Long orderId = qo.getId();
        final Order order = getById(orderId);
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

        // 关闭交易
        paymentApi.closeTrade(order.getTradeSn());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmOrder(ConfirmOrderQo qo) {
        final Long userId = qo.getUserId();
        final Long id = qo.getId();
        final Order order = getById(id);

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
    public void shippedOrder(ShippedOrderQo qo) {
        final Long userId = qo.getUserId();
        final Long id = qo.getId();
        final Order order = getById(id);

        ValidUtils.notNull(order, "未找到订单");
        if (!Lists.newArrayList(OrderStatus.ToBeDelivered.getCode()).contains(order.getOrderStatus())) {
            throw ServiceException.wrap("发货失败，订单状态错误");
        }

        final LocalDateTime createdAt = qo.getCreatedAt();
        final Order updated = new Order();
        updated.setId(id);
        updated.setReceiveTime(qo.getCreatedAt());
        updated.setOrderStatus(OrderStatus.Shipped.getCode());
        updated.setLastUpdater(userId);
        updated.setLastUpdatedAt(createdAt);
        validUpdateById(updated);
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

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void asyncOrderMessage(AsyncOrderMessageQo qo) {
        final AsyncOrderMessageQo.TradeStatusSync data = qo.getData();
        final OrderPayType payType = qo.getPayType();
        final String orderSn = data.getOutTradeSn();

        if (!TradeStatus.Success.eq(data.getTradeStatus())) {
            return;
        }

        Optional<Order> orderOpl = this.selectOneByOrderSn(orderSn);
        if (!orderOpl.isPresent()) {
            throw ServiceException.wrap("订单不存在");
        }
        final Order order = orderOpl.get();
        if (!LangUtils.equals(OrderStatus.PendingPayment.getCode(), order.getOrderStatus())) {
            log.warn("订单[orderSn={}]，状态[{}]非待付款时，被调用支付成功", orderSn, order.getOrderStatus());
            return;
        }

        // 更改订单状态
        final Order update = new Order();
        update.setId(order.getId());
        update.setOrderStatus(OrderStatus.ToBeDelivered.getCode());
        update.setPaymentTime(LocalDateTime.now());
        update.setPayType(payType.getCode());
        validUpdateById(update);
    }

    private Optional<Order> selectOneByOrderSn(String orderSn) {
        return lambdaQuery().eq(Order::getOrderSn, orderSn).oneOpt();
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(IdQo qo) {
        final Long id = qo.getId();
        final Order updated = new Order();
        updated.setId(id);
        updated.setDeleteStatus(DeleteStatus.On.getCode());
        updated.setLastUpdatedAt(qo.getCreatedAt());
        updated.setLastUpdater(qo.getUserId());
        this.validUpdateById(updated);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(UpdateOrderQo qo) {
        final Long id = qo.getId();
        final Order entity = getById(id);
        ValidUtils.notNull(entity, "订单不存在");

        Order updated = mapping.asOrder(qo);
        updated.setLastUpdatedAt(qo.getCreatedAt());
        updated.setLastUpdater(qo.getUserId());

        final BigDecimal discountAmount = updated.getDiscountAmount();

        // 如果需要重新计算价格
        if (Objects.nonNull(discountAmount) && !discountAmount.equals(entity.getDiscountAmount())) {
            if (!LangUtils.equals(OrderStatus.PendingPayment.getCode(), entity.getOrderStatus())) {
                throw ServiceException.wrap("订单非待付款状态, 无法调整价格");
            }

            final BigDecimal payAmount = entity.getPayAmount()
                .add(entity.getDiscountAmount())
                .subtract(discountAmount);
            if (payAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw ServiceException.wrap("应付价格不能为负数，价格调整失败");
            }
            updated.setPayAmount(payAmount);
        }

        this.validUpdateById(updated);
    }

    @Override
    public GoPayVo goPay(PayOrderQo qo) {
        final Long id = qo.getId();
        final Integer paymentWay = qo.getPaymentWay();
        final OrderComplexVo orderComplex = this.selectOne(id);
        if (!LangUtils.equals(OrderStatus.PendingPayment.getCode(), orderComplex.getOrderStatus())) {
            throw ServiceException.wrap("操作失败，请检查订单的支付状态");
        }

        final GoPayRo ro = new GoPayRo(orderComplex.getTradeSn(), paymentWay);
        return paymentApi.goPay(ro);
    }

    @Override
    public boolean validUpdateById(Order entity) {
        final Long orderId = entity.getId();
        final Order oldOrder = getById(orderId);
        final EntityCompare<Order> compare = new EntityCompare<>();
        final List<FieldChangeDto> changes = compare.diffUseLambda(oldOrder, entity, true,
            Order::getId, Order::getCreatedAt, Order::getCreator, Order::getLastUpdatedAt, Order::getLastUpdater);
        changeLogService.updateLog(ChangeLogRefType.OrderLog, entity.getId(), changes);
        return super.validUpdateById(entity);
    }
}
