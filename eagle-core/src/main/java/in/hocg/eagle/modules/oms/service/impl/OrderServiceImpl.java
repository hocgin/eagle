package in.hocg.eagle.modules.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.constant.CodeEnum;
import in.hocg.eagle.basic.constant.datadict.*;
import in.hocg.eagle.basic.env.Env;
import in.hocg.eagle.basic.env.EnvConfigs;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.ext.lang.SNCode;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.KeyValue;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.bmw.api.PaymentAPI;
import in.hocg.eagle.modules.bmw.api.ro.CreateTradeRo;
import in.hocg.eagle.modules.bmw.api.ro.GoPayRo;
import in.hocg.eagle.modules.bmw.pojo.vo.GoPayVo;
import in.hocg.eagle.modules.com.service.ChangeLogService;
import in.hocg.eagle.modules.mkt.api.CouponAccountAPI;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import in.hocg.eagle.modules.oms.entity.Order;
import in.hocg.eagle.modules.oms.entity.OrderItem;
import in.hocg.eagle.modules.oms.helper.order.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order.discount.DiscountHelper;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.Coupon;
import in.hocg.eagle.modules.oms.helper.order.modal.Discount;
import in.hocg.eagle.modules.oms.mapper.OrderMapper;
import in.hocg.eagle.modules.oms.mapstruct.OrderMapping;
import in.hocg.eagle.modules.oms.pojo.dto.order.OrderItemDto;
import in.hocg.eagle.modules.oms.pojo.qo.order.*;
import in.hocg.eagle.modules.oms.pojo.vo.order.AvailableDiscountVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.CalcOrderVo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.OrderItemService;
import in.hocg.eagle.modules.oms.service.OrderService;
import in.hocg.eagle.modules.pms.api.ProductAPI;
import in.hocg.eagle.modules.pms.api.SkuAPI;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import in.hocg.eagle.modules.pms.api.vo.SkuComplexVo;
import in.hocg.eagle.modules.ums.api.AccountAddressAPI;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.compare.EntityCompare;
import in.hocg.eagle.utils.compare.FieldChangeDto;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
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
    private final ProductAPI productService;
    private final OrderItemService orderItemService;
    private final PaymentAPI paymentApi;
    private final CouponAccountAPI couponAccountService;
    private final AccountAddressAPI accountAddressAPI;
    private final SkuAPI skuService;
    private final OrderMapping mapping;
    private final ChangeLogService changeLogService;
    private final SNCode snCode;

    @Override
    @ApiOperation("取消订单 - 我的订单")
    @Transactional(rollbackFor = Exception.class)
    public void cancelMyOrder(CancelOrderQo qo) {
        final Long id = qo.getId();
        ValidUtils.notNull(id, "订单信息不存在");
        final Order order = this.getById(id);
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), qo.getUserId()), "非订单所有人，操作失败");
        this.cancelOrder(qo);
    }

    @Override
    @ApiOperation("订单详情 - 我的订单")
    @Transactional(rollbackFor = Exception.class)
    public OrderComplexVo getMyOrderById(IdRo qo) {
        final Long orderId = qo.getId();
        final Long userId = qo.getUserId();
        final OrderComplexVo result = this.selectOne(orderId);
        ValidUtils.isTrue(LangUtils.equals(result.getAccountId(), userId), "非订单拥有人");
        return result;
    }

    @Override
    @ApiOperation("确认收货 - 我的订单")
    @Transactional(rollbackFor = Exception.class)
    public void confirmMyOrder(ConfirmOrderQo qo) {
        final Order order = this.getById(qo.getId());
        ValidUtils.notNull(order, "未找到订单");
        ValidUtils.isTrue(LangUtils.equals(order.getAccountId(), qo.getUserId()), "非订单所有人，操作失败");
        this.confirmOrder(qo);
    }

    @Override
    @ApiOperation("分页查询 - 我的订单")
    @Transactional(rollbackFor = Exception.class)
    public IPage<OrderComplexVo> pagingMyOrder(OrderPagingQo qo) {
        qo.setAccountId(qo.getUserId());
        return this.paging(qo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CalcOrderVo calcOrder(CalcOrderQo qo) {
        final CalcOrderVo result = new CalcOrderVo();
        final Long accountId = qo.getUserId();
        final Long userCouponId = qo.getUserCouponId();

        final GeneralOrder generalOrder = this.createOrder(qo);

        if (Objects.nonNull(userCouponId)) {
            CouponAccountComplexVo couponVo = couponAccountService.selectOne(userCouponId);
            ValidUtils.isTrue(LangUtils.equals(couponVo.getAccountId(), accountId), "无法使用该优惠券");
            Coupon coupon = DiscountHelper.createCoupon(couponVo);
            generalOrder.use(coupon);

            List<CalcOrderVo.DiscountInfo> discounts = Lists.newArrayList();
            discounts.add(new CalcOrderVo.DiscountInfo()
                .setId((Long) coupon.id())
                .setTitle(coupon.title())
                .setType(DiscountType.Coupon.getCode())
                .setDiscountAmount(coupon.getDiscountTotalAmount()));
            result.setDiscounts(discounts);
        }
        result.setDefaultAddress(accountAddressAPI.getDefaultByUserId(accountId).orElse(null));

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

        result.setItems(orderItems.parallelStream()
            .map((item) -> {
                final String specData = item.getProductSpecData();
                final CalcOrderVo.OrderItem itemResult = mapping.asCalcOrderVoOrderItem(item);
                return itemResult.setTotalAmount(itemResult.getPrice().multiply(new BigDecimal(itemResult.getQuantity())))
                    .setSpec(JSON.parseArray(specData, KeyValue.class));
            })
            .collect(Collectors.toList()));
        result.setTotalAmount(totalAmount);
        result.setPayAmount(payAmount);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createMyOrder(CreateOrderQo qo) {
        final Long currentUserId = qo.getUserId();
        final CalcOrderVo calcResult = this.calcOrder(qo);
        final List<CalcOrderVo.DiscountInfo> discounts = calcResult.getDiscounts();

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

        // 处理优惠信息
        if (!CollectionUtils.isEmpty(discounts)) {
            for (CalcOrderVo.DiscountInfo discount : discounts) {
                final DiscountType discountType = CodeEnum.ofThrow(discount.getType(), DiscountType.class);
                switch (discountType) {
                    case Coupon: {
                        final Long userCouponId = discount.getId();
                        final BigDecimal discountAmount = discount.getDiscountAmount();
                        order.setCouponAccountId(userCouponId);
                        order.setCouponAmount(discountAmount);
                        if (!couponAccountService.updateUsedStatus(userCouponId, discountAmount)) {
                            throw ServiceException.wrap("优惠券已被使用");
                        }
                        break;
                    }
                    default:
                        throw ServiceException.wrap("系统繁忙，请稍后");
                }
            }
        }
        validInsert(order);
        final Long orderId = order.getId();

        final List<CalcOrderVo.OrderItem> orderItems = calcResult.getItems();
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
        return tradeSn;
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

        final BigDecimal discountTotalAmount = LangUtils.getOrDefault(result.getDiscountAmount(), BigDecimal.ZERO).add(LangUtils.getOrDefault(result.getCouponAmount(), BigDecimal.ZERO));
        result.setDiscountTotalAmount(discountTotalAmount);
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
            couponAccountService.updateUnusedStatus(accountCouponId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(IdRo qo) {
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

        return paymentApi.goPay(new GoPayRo()
            .setTradeSn(orderComplex.getTradeSn())
            .setPaymentWay(paymentWay));
    }

    @Override
    @ApiOperation("获取可用优惠券")
    @Transactional(rollbackFor = Exception.class)
    public AvailableDiscountVo listAvailableCouponsByOrder(CalcOrderQo qo) {
        final Long userId = qo.getUserId();
        final GeneralOrder generalOrder = this.createOrder(qo);
        final List<CouponAccountComplexVo> couponComplexs = couponAccountService.selectListByAccountId(userId);
        final List<Discount> allCoupon = couponComplexs.parallelStream().map(DiscountHelper::createCoupon).collect(Collectors.toList());

        // 可用优惠信息
        final List<Discount> usableDiscount = generalOrder.getUsableDiscount(allCoupon);
        final BiFunction<Discount, Discount, Boolean> isSame = (coupon, discount) -> {
            if (discount instanceof Coupon && coupon.id() == discount.id()) {
                return true;
            }
            return false;
        };

        // 不可用优惠信息
        final List<Discount> unusableDiscount = LangUtils.getMixed(allCoupon, usableDiscount, isSame);

        final AvailableDiscountVo result = new AvailableDiscountVo();
        result.setAvailableCoupon(usableDiscount.parallelStream().map(DiscountHelper::convertComplex).collect(Collectors.toList()));
        result.setUnavailableCoupon(unusableDiscount.parallelStream().map(DiscountHelper::convertComplex).collect(Collectors.toList()));
        return result;
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

    private GeneralOrder createOrder(CalcOrderQo qo) {
        final Long accountId = qo.getUserId();
        final LocalDateTime createdAt = qo.getCreatedAt();
        final List<CalcOrderQo.Item> items = qo.getItems();

        // 1. 检查商品
        final List<GeneralProduct> products = items.stream()
            .map(item -> {
                final SkuComplexVo sku = skuService.selectOne(item.getSkuId());
                ValidUtils.notNull(sku, "商品规格错误");
                final Long productId = sku.getProductId();
                final ProductComplexVo product = productService.selectOne(productId);
                ValidUtils.notNull(product, "未找到商品");
                ValidUtils.isTrue(DeleteStatus.Off.eq(product.getDeleteStatus()), "未找到商品");
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

        return new GeneralOrder(products, accountId, OrderSourceType.APP, createdAt);
    }
}
