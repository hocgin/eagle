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
import in.hocg.eagle.modules.oms.helper.order.GeneralOrderHelper;
import in.hocg.eagle.modules.oms.helper.order.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order.discount.DiscountHelper;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.Coupon;
import in.hocg.eagle.modules.oms.helper.order.modal.Discount;
import in.hocg.eagle.modules.oms.mapper.OrderMapper;
import in.hocg.eagle.modules.oms.mapstruct.OrderMapping;
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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

        final GeneralOrderHelper orderHelper = new GeneralOrderHelper();

        List<Discount> useDiscounts = Lists.newArrayList();

        // 优惠券信息
        if (Objects.nonNull(userCouponId)) {
            CouponAccountComplexVo couponVo = couponAccountService.selectOne(userCouponId);
            ValidUtils.isTrue(LangUtils.equals(couponVo.getAccountId(), accountId), "无法使用该优惠券");
            useDiscounts.add(DiscountHelper.createCoupon(couponVo));
        }
        final GeneralOrder generalOrder = orderHelper.useDiscounts(this.createOrder(qo), useDiscounts);

        final BigDecimal totalAmount = generalOrder.getPreDiscountTotalAmount();
        final BigDecimal payAmount = generalOrder.getPreferentialAmount();
        final BigDecimal discountTotalAmount = generalOrder.getDiscountTotalAmount();

        // 1. 获取订单项信息
        List<CalcOrderVo.OrderItem> orderItems = generalOrder.mapProduct(item -> {
            final CalcOrderVo.OrderItem productItem = new CalcOrderVo.OrderItem()
                .setSpecData(item.getProductSpecData())
                .setImageUrl(item.getProductPic())
                .setSkuId(item.getProductSkuId())
                .setSkuCode(item.getProductSkuCode())
                .setQuantity(item.getProductQuantity())
                .setPrice(item.getProductPrice())
                .setTitle(item.getProductName())
                .setProductId(item.getProductId())
                .setDiscountAmount(item.getDiscountPrice())
                .setRealAmount(item.getPreferentialPrice())
                .setSpec(JSON.parseArray(item.getProductSpecData(), KeyValue.class))
                .setTotalAmount(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));

            return productItem;
        });
        result.setItems(orderItems);

        // 2. 优惠信息
        List<CalcOrderVo.DiscountInfo> discounts = Lists.newArrayList();
        for (Discount discount : generalOrder.getUseDiscount()) {
            final BigDecimal discountAmount = discount.getDiscountTotalAmount();
            final Serializable id = discount.id();
            final String title = discount.title();

            CalcOrderVo.DiscountInfo discountInfo;
            if (discount instanceof Coupon) {
                discountInfo = new CalcOrderVo.DiscountInfo()
                    .setId((Long) id)
                    .setTitle(title)
                    .setType(DiscountType.Coupon.getCode())
                    .setDiscountAmount(discountAmount);
            } else {
                throw new UnsupportedOperationException("未知的优惠信息");
            }
            discounts.add(discountInfo);
        }
        final BigDecimal couponDiscountAmount = discounts.parallelStream().filter(item -> DiscountType.Coupon.eq(item.getType()))
            .map(CalcOrderVo.DiscountInfo::getDiscountAmount)
            .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        result.setDiscounts(discounts);
        result.setTotalAmount(totalAmount);
        result.setCouponDiscountAmount(couponDiscountAmount);
        result.setDiscountTotalAmount(discountTotalAmount);
        result.setPayAmount(payAmount);
        result.setDefaultAddress(accountAddressAPI.getDefaultByUserId(accountId).orElse(null));
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createMyOrder(CreateOrderQo qo) {
        final Long currentUserId = qo.getUserId();
        final CreateOrderQo.Receiver receiver = qo.getReceiver();
        final String remark = qo.getRemark();

        final CalcOrderVo calcResult = this.calcOrder(qo);
        final List<CalcOrderVo.DiscountInfo> discounts = calcResult.getDiscounts();
        final BigDecimal discountTotalAmount = calcResult.getDiscountTotalAmount();
        final BigDecimal totalAmount = calcResult.getTotalAmount();
        final BigDecimal payAmount = calcResult.getPayAmount();
        final BigDecimal couponDiscountAmount = calcResult.getCouponDiscountAmount();

        final Order order = new Order()
            .setOrderSn(snCode.getOrderSNCode())
            .setCouponAccountId(qo.getUserCouponId())
            .setSourceType(qo.getSourceType())
            .setAccountId(currentUserId)
            .setOrderStatus(OrderStatus.PendingPayment.getCode())
            .setAutoConfirmDay(15)
            .setRemark(remark)
            // 金额相关
            .setCouponDiscountAmount(couponDiscountAmount)
            .setFreightAmount(BigDecimal.ZERO)
            .setTotalAmount(totalAmount)
            .setDiscountAmount(discountTotalAmount)
            .setPayAmount(payAmount)
            // 收货人信息
            .setReceiverName(receiver.getName())
            .setReceiverPhone(receiver.getPhone())
            .setReceiverPostCode(receiver.getPostCode())
            .setReceiverProvince(receiver.getProvince())
            .setReceiverCity(receiver.getCity())
            .setReceiverRegion(receiver.getRegion())
            .setReceiverDetailAddress(receiver.getDetailAddress())
            .setReceiverAdCode(receiver.getAdCode())
            // 创建信息
            .setCreatedAt(qo.getCreatedAt())
            .setCreator(currentUserId);

        // 处理优惠信息
        if (!CollectionUtils.isEmpty(discounts)) {
            for (CalcOrderVo.DiscountInfo discount : discounts) {
                final DiscountType discountType = CodeEnum.ofThrow(discount.getType(), DiscountType.class);
                if (discountType == DiscountType.Coupon) {
                    final Long userCouponId = discount.getId();
                    final BigDecimal discountAmount = discount.getDiscountAmount();
                    if (!couponAccountService.updateUsedStatus(userCouponId, discountAmount)) {
                        throw ServiceException.wrap("优惠券已被使用");
                    }
                } else {
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
            .setDiscountAmount(item.getDiscountAmount())
            .setTotalAmount(item.getTotalAmount())
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
        String notifyUrl = String.format("%s/api/order/async", configs.getHostname());
        final String tradeSn = paymentApi.createTrade(new CreateTradeRo(paymentAppSn, orderSn, payAmount).setNotifyUrl(notifyUrl));
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
        return this.convertComplex(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<OrderComplexVo> paging(OrderPagingQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
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

    private OrderComplexVo convertComplex(Order entity) {
        final OrderComplexVo result = mapping.asOrderComplexVo(entity);

        final BigDecimal discountTotalAmount = LangUtils.getOrDefault(result.getDiscountAmount(), BigDecimal.ZERO)
            .add(LangUtils.getOrDefault(result.getAdjustmentDiscountAmount(), BigDecimal.ZERO));
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

        final BigDecimal adjustmentDiscountAmount = updated.getAdjustmentDiscountAmount();

        // 如果需要重新计算价格
        if (Objects.nonNull(adjustmentDiscountAmount) && !adjustmentDiscountAmount.equals(entity.getAdjustmentDiscountAmount())) {
            this.updateAdjustmentDiscountAmount(id, new AdjustmentDiscountQo().setAdjustmentDiscountAmount(adjustmentDiscountAmount));
        }
        updated.setAdjustmentDiscountAmount(null);
        this.validUpdateById(updated);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAdjustmentDiscountAmount(Long orderId, AdjustmentDiscountQo qo) {
        final BigDecimal adjustmentDiscountAmount = qo.getAdjustmentDiscountAmount();

        final Order entity = getById(orderId);
        ValidUtils.notNull(entity, "订单不存在");
        if (!LangUtils.equals(OrderStatus.PendingPayment.getCode(), entity.getOrderStatus())) {
            throw ServiceException.wrap("订单非待付款状态, 无法调整价格");
        }

        // 1. 调整订单价格
        {
            final BigDecimal oldAdjustmentDiscountAmount = entity.getAdjustmentDiscountAmount();
            final BigDecimal payAmount = entity.getPayAmount()
                .add(oldAdjustmentDiscountAmount)
                .subtract(adjustmentDiscountAmount);
            if (payAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw ServiceException.wrap("应付价格不能为负数，价格调整失败");
            }
            final Order updated = new Order();
            updated.setId(orderId);
            updated.setAdjustmentDiscountAmount(adjustmentDiscountAmount);
            updated.setPayAmount(payAmount);
            final Integer changeRow = baseMapper.updateAdjustmentDiscountAmountIf(updated, oldAdjustmentDiscountAmount);
            ValidUtils.isTrue(changeRow == 1, "价格调整失败");
            this.log(updated);
        }

        // 2. 重新分解商品价格
        {
            BigDecimal totalAdjustmentDiscountAmount = adjustmentDiscountAmount;
            final List<OrderItem> orderItems = orderItemService.selectListByOrderId2(orderId);
            final int itemsCount = orderItems.size();
            final BigDecimal itemAdjustmentDiscountAmount = totalAdjustmentDiscountAmount.divide(new BigDecimal(itemsCount), 2, RoundingMode.DOWN);
            OrderItem updated;
            OrderItem orderItem;
            for (int i = 0; i < itemsCount; i++) {
                orderItem = orderItems.get(0);
                final BigDecimal oldAdjustmentDiscountAmount = orderItem.getAdjustmentDiscountAmount();
                updated = new OrderItem();
                updated.setId(orderItem.getId());
                // 如果是最后一项
                if (i == (itemsCount - 1)) {
                    updated.setAdjustmentDiscountAmount(totalAdjustmentDiscountAmount);
                } else {
                    updated.setAdjustmentDiscountAmount(itemAdjustmentDiscountAmount);
                }
                final BigDecimal realAmount = orderItem.getRealAmount()
                    .add(oldAdjustmentDiscountAmount)
                    .subtract(updated.getAdjustmentDiscountAmount());
                updated.setRealAmount(realAmount);

                totalAdjustmentDiscountAmount = totalAdjustmentDiscountAmount.subtract(itemAdjustmentDiscountAmount);
                final Integer changeRow = orderItemService.updateAdjustmentDiscountAmountIf(updated, oldAdjustmentDiscountAmount);
                ValidUtils.isTrue(changeRow == 1, "价格调整失败");
            }
        }


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
        final List<CouponAccountComplexVo> couponComplexes = couponAccountService.selectListByAccountId(userId);
        final List<Discount> allCoupon = couponComplexes.parallelStream()
            .map(DiscountHelper::createCoupon).collect(Collectors.toList());

        final BiFunction<Discount, Discount, Boolean> isSame = (coupon, discount) -> coupon.id() == discount.id();

        // 可用优惠信息
        final List<Discount> usableDiscount = generalOrder.getUsableDiscount(allCoupon);
        // 不可用优惠信息
        final List<Discount> unusableDiscount = LangUtils.removeIfExits(allCoupon, usableDiscount, isSame);

        final Map<Long, CouponAccountComplexVo> map = LangUtils.toMap(couponComplexes, CouponAccountComplexVo::getId);

        final AvailableDiscountVo result = new AvailableDiscountVo();
        result.setAvailableCoupon(usableDiscount.parallelStream()
            .map(Discount::id)
            .map(serializable -> ((Long) serializable)).map(map::get)
            .filter(Objects::nonNull).collect(Collectors.toList()));
        result.setUnavailableCoupon(unusableDiscount.parallelStream()
            .map(Discount::id)
            .map(serializable -> ((Long) serializable)).map(map::get)
            .filter(Objects::nonNull).collect(Collectors.toList()));
        return result;
    }

    @Override
    public boolean validUpdateById(Order entity) {
        this.log(entity);
        return super.validUpdateById(entity);
    }

    @Override
    public void log(Order newOrder) {
        final Long id = newOrder.getId();
        final Order oldOrder = getById(id);
        final List<FieldChangeDto> changes = new EntityCompare<Order>().diffUseLambda(oldOrder, newOrder, true,
            Order::getId, Order::getCreatedAt, Order::getCreator, Order::getLastUpdatedAt, Order::getLastUpdater);
        changeLogService.updateLog(ChangeLogRefType.OrderLog, id, changes);
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
