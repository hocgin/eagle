package in.hocg.eagle.modules.bmw.helper.payment;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.constant.datadict.*;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.lang.SNCode;
import in.hocg.eagle.modules.bmw.entity.NotifyAppLog;
import in.hocg.eagle.modules.bmw.entity.PaymentRecord;
import in.hocg.eagle.modules.bmw.entity.PaymentRefund;
import in.hocg.eagle.modules.bmw.entity.PaymentTransaction;
import in.hocg.eagle.modules.bmw.helper.payment.dto.PaymentWay;
import in.hocg.eagle.modules.bmw.helper.payment.dto.SyncTradeStatusResult;
import in.hocg.eagle.modules.bmw.helper.payment.request.PaymentRequest;
import in.hocg.eagle.modules.bmw.helper.payment.request.PaymentRequestResult;
import in.hocg.eagle.modules.bmw.helper.payment.request.RefundRequest;
import in.hocg.eagle.modules.bmw.helper.payment.request.RefundRequestResult;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.AllInMessageResolve;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.MessageType;
import in.hocg.eagle.modules.bmw.mapstruct.PaymentRefundMapping;
import in.hocg.eagle.modules.bmw.mapstruct.PaymentTransactionMapping;
import in.hocg.eagle.modules.bmw.pojo.qo.CreatePaymentTransactionQo;
import in.hocg.eagle.modules.bmw.pojo.qo.GoPayQo;
import in.hocg.eagle.modules.bmw.pojo.qo.GoRefundQo;
import in.hocg.eagle.modules.bmw.pojo.vo.RefundInfo;
import in.hocg.eagle.modules.bmw.pojo.vo.TransactionInfo;
import in.hocg.eagle.modules.bmw.service.NotifyAppLogService;
import in.hocg.eagle.modules.bmw.service.PaymentRecordService;
import in.hocg.eagle.modules.bmw.service.PaymentRefundService;
import in.hocg.eagle.modules.bmw.service.PaymentTransactionService;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.string.JsonUtils;
import in.hocg.eagle.utils.web.RequestUtils;
import in.hocg.payment.PaymentMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by hocgin on 2020/5/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentServiceImpl
    implements PaymentService {
    private final PaymentTransactionService paymentTransactionService;
    private final PaymentRecordService paymentRecordService;
    private final PaymentRefundService paymentRefundService;
    private final NotifyAppLogService notifyAppLogService;

    private final PaymentTransactionMapping paymentTransactionMapping;
    private final PaymentRefundMapping paymentRefundMapping;
    private final AllInMessageResolve messageResolve;
    private final SNCode snCode;

    @Override
    public void getPaymentWayList() {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createPaymentTransaction(CreatePaymentTransactionQo data) {
        LocalDateTime now = LocalDateTime.now();
        final String clientIp = SpringContext.getRequest().map(RequestUtils::getClientIP).orElse(null);

        PaymentTransaction entity = paymentTransactionMapping.asPaymentTransaction(data)
            .setTransactionSn(snCode.getTransactionSNCode())
            .setTradeStatus(TradeStatus.Init.getCode())
            .setCreatedAt(now)
            .setCreatedIp(clientIp);
        paymentTransactionService.validInsert(entity);
        return entity.getTransactionSn();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closePaymentTransaction(String transactionSn) {
        LocalDateTime now = LocalDateTime.now();
        final String clientIp = SpringContext.getRequest().map(RequestUtils::getClientIP).orElse(null);

        final PaymentTransaction transaction = paymentTransactionService.selectOneByTransactionSn(transactionSn)
            .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
        final Long transactionId = transaction.getId();

        PaymentTransaction update = new PaymentTransaction().setTradeStatus(TradeStatus.Closed.getCode()).setUpdatedAt(now).setUpdatedIp(clientIp);
        boolean isOk = paymentTransactionService.updateOneByIdAndTradeStatus(update, transactionId, TradeStatus.Init.getCode(), TradeStatus.Wait.getCode());
        ValidUtils.isTrue(isOk, "系统繁忙");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentRequestResult goPay(GoPayQo data) {
        LocalDateTime now = LocalDateTime.now();
        final String clientIp = SpringContext.getRequest().map(RequestUtils::getClientIP).orElse(null);

        // 保存日志
        final String transactionSn = data.getTransactionSn();
        final PaymentWay paymentWay = IntEnum.of(data.getPaymentWay(), PaymentWay.class).orElseThrow(() -> ServiceException.wrap("暂不支持该交易方式"));
        final PaymentTransaction transaction = paymentTransactionService.selectOneByTransactionSn(transactionSn)
            .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
        final Long transactionId = transaction.getId();

        if (TradeStatus.Init.eq(transaction.getTradeStatus())) {
            PaymentTransaction update = new PaymentTransaction().setTradeStatus(TradeStatus.Wait.getCode()).setUpdatedAt(now).setUpdatedIp(clientIp);
            boolean isOk = paymentTransactionService.updateOneByIdAndTradeStatus(update, transactionId, TradeStatus.Init.getCode());
            ValidUtils.isTrue(isOk, "系统繁忙");
        } else if (TradeStatus.Wait.eq(transaction.getTradeStatus())) {
            log.info("不用进行操作");
        } else {
            throw ServiceException.wrap("系统繁忙");
        }
        final String orderSn = transaction.getAppOrderSn();

        // 新增支付记录
        paymentRecordService.validInsert(new PaymentRecord()
            .setPaymentWay(paymentWay.getCode())
            .setCreatedAt(now)
            .setCreatedIp(clientIp)
            .setTransactionId(transactionId));

        return PaymentRequest.builder()
            .orderSn(orderSn)
            .payAmount(transaction.getTotalFee())
            .paymentWay(paymentWay)
            .build()
            .request();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePaymentCallback(PaymentWay paymentWay, String transactionSn, String tradeNo, boolean isSuccess) {
        final String clientIp = SpringContext.getRequest().map(RequestUtils::getClientIP).orElse(null);
        final LocalDateTime now = LocalDateTime.now();

        final PaymentTransaction transaction = paymentTransactionService.selectOneByTransactionSn(transactionSn)
            .orElseThrow(() -> ServiceException.wrap("支付失败"));

        final TradeStatus tradeStatus = isSuccess ? TradeStatus.Done : TradeStatus.Fail;
        final PaymentTransaction updateTransaction = new PaymentTransaction()
            .setId(transaction.getId())
            .setPaymentWay(paymentWay.getCode())
            .setTradeStatus(tradeStatus.getCode())
            .setTradeNo(tradeNo)
            .setPaymentAt(now)
            .setUpdatedAt(now)
            .setUpdatedIp(clientIp);

        boolean isOk = paymentTransactionService.updateOneByIdAndTradeStatus(updateTransaction, transaction.getId(), TradeStatus.Wait.getCode());
        ValidUtils.isTrue(isOk, "支付失败");
        ((PaymentService) AopContext.currentProxy()).sendPaymentAsyncNotify(transaction.getId(), isSuccess);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refund(GoRefundQo data) {
        final String clientIp = SpringContext.getRequest().map(RequestUtils::getClientIP).orElse(null);
        final LocalDateTime now = LocalDateTime.now();
        final String transactionSn = data.getTransactionSn();

        final PaymentTransaction transaction = paymentTransactionService.selectOneByTransactionSn(transactionSn)
            .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
        if (!TradeStatus.Done.eq(transaction.getTradeStatus())) {
            ValidUtils.fail("交易未完成");
        }
        final PaymentWay paymentWay = IntEnum.of(transaction.getPaymentWay(), PaymentWay.class)
            .orElseThrow(() -> ServiceException.wrap("暂不支持该交易方式"));

        PaymentRefund entity = paymentRefundMapping.asPaymentRefund(data)
            .setRefundSn(snCode.getRefundSNCode())
            .setRefundStatus(RefundStatus.Wait.getCode())
            .setCreatedAt(now)
            .setCreatedIp(clientIp);

        final RefundRequestResult result = RefundRequest.builder()
            .paymentWay(paymentWay)
            .transactionSn(transaction.getTransactionSn())
            .tradeNo(transaction.getTradeNo())
            .refundSn(entity.getRefundSn())
            .totalFee(transaction.getTotalFee())
            .refundFee(entity.getRefundFee())
            .build().request();

        entity.setRefundTradeNo(result.getRefundTradeNo());
        paymentRefundService.validInsert(entity);
        return entity.getRefundSn();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleRefundCallback(PaymentWay paymentWay, String refundSn, String refundTradeNo, boolean isSuccess) {
        final String clientIp = SpringContext.getRequest().map(RequestUtils::getClientIP).orElse(null);
        final LocalDateTime now = LocalDateTime.now();

        final PaymentRefund refund = paymentRefundService.selectOneByRefundSn(refundSn)
            .orElseThrow(() -> ServiceException.wrap("退款失败"));

        final RefundStatus refundStatus = isSuccess ? RefundStatus.Done : RefundStatus.Fail;
        final PaymentRefund updated = new PaymentRefund()
            .setRefundStatus(refundStatus.getCode())
            .setPaymentWay(paymentWay.getCode())
            .setUpdateIp(clientIp)
            .setUpdatedAt(now);

        boolean isOk = paymentRefundService.updateOneByIdAndTradeStatus(updated, refund.getId(), RefundStatus.Wait.getCode());
        ValidUtils.isTrue(isOk, "退款失败");
        ((PaymentService) AopContext.currentProxy()).sendRefundAsyncNotify(refund.getId(), isSuccess);
    }

    @Async
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendRefundAsyncNotify(Long refundId, boolean isSuccess) {

        final PaymentRefund refund = paymentRefundService.getById(refundId);
        ValidUtils.isTrue(Objects.nonNull(refund), "退款单据不存在");
        final RefundStatus refundStatus = IntEnum.of(refund.getRefundStatus(), RefundStatus.class)
            .orElseThrow(() -> ServiceException.wrap("退款状态错误"));

        final NotifyEvent notifyEvent = NotifyEvent.Refund;
        final String refundSn = refund.getRefundSn();
        final Integer paymentWay = refund.getPaymentWay();
        final String tradeNo = refund.getRefundTradeNo();
        final BigDecimal refundFee = refund.getRefundFee();
        final NotifyAppLog entity = new NotifyAppLog()
            .setPaymentWay(paymentWay)
            .setTradeNo(tradeNo)
            .setOrderSn(refundSn)
            .setNotifyEventStatus(NotifyEventStatus.Pending.getCode())
            .setNotifyEvent(notifyEvent.getCode())
            .setTotalFee(refundFee)
            .setCreatedAt(LocalDateTime.now());
        notifyAppLogService.validInsert(entity);

        // 通知
        boolean isOk = this.callSyncRefund(SyncTradeStatusResult.builder()
            .notifyEvent(notifyEvent.getCode())
            .totalFee(refundFee)
            .paymentWay(paymentWay)
            .tradeNo(tradeNo)
            .orderSn(refundSn)
            .status(SyncTradeStatusResult.transform(refundStatus).getCode())
            .build());
        final LocalDateTime finishAt = LocalDateTime.now();
        notifyAppLogService.validUpdateById(new NotifyAppLog()
            .setId(entity.getId())
            .setNotifyEventStatus((isOk ? NotifyEventStatus.Success : NotifyEventStatus.Fail).getCode())
            .setFinishAt(finishAt)
            .setUpdatedAt(finishAt));
    }

    @Async
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendPaymentAsyncNotify(Long transactionId, boolean isSuccess) {
        final PaymentTransaction transaction = paymentTransactionService.getById(transactionId);
        ValidUtils.isTrue(Objects.nonNull(transaction), "付款单据不存在");
        final TradeStatus status = IntEnum.of(transaction.getTradeStatus(), TradeStatus.class)
            .orElseThrow(() -> ServiceException.wrap("付款单据状态错误"));

        final NotifyEvent notifyEvent = NotifyEvent.Paid;
        final BigDecimal totalFee = transaction.getTotalFee();
        final Integer paymentWay = transaction.getPaymentWay();
        final String tradeNo = transaction.getTradeNo();
        final String transactionSn = transaction.getTransactionSn();
        final NotifyAppLog entity = new NotifyAppLog()
            .setPaymentWay(paymentWay)
            .setTradeNo(tradeNo)
            .setOrderSn(transactionSn)
            .setNotifyEventStatus(NotifyEventStatus.Pending.getCode())
            .setNotifyEvent(notifyEvent.getCode())
            .setTotalFee(totalFee)
            .setCreatedAt(LocalDateTime.now());
        notifyAppLogService.validInsert(entity);

        // 通知
        boolean isOk = this.callSyncPayment(SyncTradeStatusResult.builder()
            .notifyEvent(notifyEvent.getCode())
            .totalFee(totalFee)
            .paymentWay(paymentWay)
            .tradeNo(tradeNo)
            .orderSn(transactionSn)
            .status(SyncTradeStatusResult.transform(status).getCode())
            .build());
        final LocalDateTime finishAt = LocalDateTime.now();
        notifyAppLogService.validUpdateById(new NotifyAppLog()
            .setId(entity.getId())
            .setNotifyEventStatus((isOk ? NotifyEventStatus.Success : NotifyEventStatus.Fail).getCode())
            .setFinishAt(finishAt)
            .setUpdatedAt(finishAt));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionInfo queryTransaction(String transactionSn) {
        final PaymentTransaction transaction = paymentTransactionService.selectOneByTransactionSn(transactionSn)
            .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
        return paymentTransactionMapping.asTransactionInfo(transaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefundInfo queryRefund(String refundSn) {
        final PaymentRefund refund = paymentRefundService.selectOneByRefundSn(refundSn)
            .orElseThrow(() -> ServiceException.wrap("未找到退款单据"));
        return paymentRefundMapping.asRefundInfo(refund);
    }

    @Override
    public String handleMessage(Integer channel, Integer feature, String data) {
        PaymentMessage.Result result = messageResolve.handle(MessageType.of(channel, feature), data);
        return result.string();
    }

    private boolean callSyncRefund(SyncTradeStatusResult result) {
        log.info("同步退款信息: {}", JsonUtils.toJSONString(result));
        return true;
    }

    private boolean callSyncPayment(SyncTradeStatusResult result) {
        log.info("同步支付信息: {}", JsonUtils.toJSONString(result));
        return false;
    }
}
