package in.hocg.eagle.modules.bmw.service.impl;

import cn.hutool.core.util.StrUtil;
import in.hocg.eagle.basic.constant.CodeEnum;
import in.hocg.eagle.basic.constant.datadict.*;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.ext.lang.SNCode;
import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.modules.bmw.api.ro.CreateTradeRo;
import in.hocg.eagle.modules.bmw.api.ro.GoPayRo;
import in.hocg.eagle.modules.bmw.api.ro.QueryPaymentWayRo;
import in.hocg.eagle.modules.bmw.api.vo.PaymentWayVo;
import in.hocg.eagle.modules.bmw.api.vo.QueryAsyncVo;
import in.hocg.eagle.modules.bmw.api.vo.RefundStatusSync;
import in.hocg.eagle.modules.bmw.api.vo.TradeStatusSync;
import in.hocg.eagle.modules.bmw.datastruct.PaymentMapping;
import in.hocg.eagle.modules.bmw.datastruct.PaymentTradeMapping;
import in.hocg.eagle.modules.bmw.datastruct.RefundRecordMapping;
import in.hocg.eagle.modules.bmw.entity.PaymentPlatform;
import in.hocg.eagle.modules.bmw.entity.*;
import in.hocg.eagle.modules.bmw.helper.payment.pojo.request.CloseTradeRequest;
import in.hocg.eagle.modules.bmw.helper.payment.pojo.request.GoPaymentRequest;
import in.hocg.eagle.modules.bmw.helper.payment.pojo.request.GoRefundRequest;
import in.hocg.eagle.modules.bmw.helper.payment.pojo.response.GoPaymentResponse;
import in.hocg.eagle.modules.bmw.helper.payment.pojo.response.GoRefundResponse;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.AllInMessageResolve;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.MessageContext;
import in.hocg.eagle.modules.bmw.pojo.ro.GoRefundRo;
import in.hocg.eagle.modules.bmw.pojo.ro.PaymentMessageRo;
import in.hocg.eagle.modules.bmw.pojo.ro.RefundMessageRo;
import in.hocg.eagle.modules.bmw.pojo.vo.GoPayVo;
import in.hocg.eagle.modules.bmw.pojo.vo.GoRefundVo;
import in.hocg.eagle.modules.bmw.pojo.vo.NotifyAppAsyncVo;
import in.hocg.eagle.modules.bmw.pojo.vo.WaitPaymentTradeVo;
import in.hocg.eagle.modules.bmw.service.*;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.lambda.map.LambdaMap;
import in.hocg.eagle.utils.lambda.map.StringMap;
import in.hocg.eagle.utils.string.JsonUtils;
import in.hocg.payment.PaymentMessage;
import io.netty.handler.timeout.TimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentServiceImpl implements PaymentService {
    private final PaymentAppService paymentAppService;
    private final PaymentTradeService paymentTradeService;
    private final PaymentRecordService paymentRecordService;
    private final PaymentPlatformService paymentPlatformService;
    private final RefundRecordService refundRecordService;
    private final NotifyAppService notifyAppService;
    private final NotifyAppLogService notifyAppLogService;
    private final PaymentWayRuleService paymentWayRuleService;

    private final RefundRecordMapping refundRecordMapping;
    private final PaymentTradeMapping paymentTradeMapping;
    private final PaymentMapping paymentMapping;
    private final AllInMessageResolve messageResolve;
    private final SNCode snCode;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeTrade(String tradeSn) {
        LocalDateTime now = LocalDateTime.now();
        final String clientIp = SpringContext.getClientIP().orElse(null);

        final PaymentTrade trade = paymentTradeService.selectOneByTradeSn(tradeSn)
            .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
        final Long tradeId = trade.getId();

        // 如果是初始化状态可直接关闭
        if (TradeStatus.Init.eq(trade.getTradeStatus())) {
            PaymentTrade update = new PaymentTrade().setFinishAt(now).setTradeStatus(TradeStatus.Closed.getCode()).setUpdatedAt(now).setUpdatedIp(clientIp);
            boolean isOk = paymentTradeService.updateOneByIdAndTradeStatus(update, tradeId, TradeStatus.Init.getCode());
            ValidUtils.isTrue(isOk, "系统繁忙");
            return;
        }

        PaymentTrade update = new PaymentTrade().setFinishAt(now).setTradeStatus(TradeStatus.Closed.getCode()).setUpdatedAt(now).setUpdatedIp(clientIp);
        boolean isOk = paymentTradeService.updateOneByIdAndTradeStatus(update, tradeId, TradeStatus.Wait.getCode());
        ValidUtils.isTrue(isOk, "系统繁忙");

        final List<PaymentRecord> paymentRecords = paymentRecordService.selectListByTradeId(trade.getId());
        final Map<Long, PaymentRecord> recordsMap = LangUtils.toMap(paymentRecords, PaymentRecord::getPaymentPlatformId);
        PaymentPlatform paymentPlatform;
        in.hocg.eagle.basic.constant.datadict.PaymentPlatform platform;
        for (Map.Entry<Long, PaymentRecord> entry : recordsMap.entrySet()) {
            final Long paymentPlatformId = entry.getKey();
            final PaymentRecord record = entry.getValue();
            paymentPlatform = paymentPlatformService.getById(paymentPlatformId);
            final String platformAppid = paymentPlatform.getPlatformAppid();
            platform = CodeEnum.of(paymentPlatform.getPlatformType(), in.hocg.eagle.basic.constant.datadict.PaymentPlatform.class)
                .orElseThrow(() -> ServiceException.wrap("暂不支持该平台方式"));

            boolean isClosedOk = new CloseTradeRequest(trade.getTradeSn(), platformAppid, platform).request();
            log.info("交易单:[tradeSn={}]的交易记录:[ID={}], 关闭结果:[{}]", tradeSn, record.getId(), isClosedOk);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createTrade(CreateTradeRo ro) {
        LocalDateTime now = LocalDateTime.now();
        final String clientIp = SpringContext.getClientIP().orElse(null);

        final Long appid = paymentAppService.selectOneByAppSn(ro.getAppSn())
            .orElseThrow(() -> ServiceException.wrap("未授权接入方")).getId();

        final String tradeSn = snCode.getTransactionSNCode();

        PaymentTrade entity = paymentTradeMapping.asPaymentTrade(ro)
            .setAppId(appid)
            .setTradeSn(tradeSn)
            .setTradeStatus(TradeStatus.Init.getCode())
            .setCreatedAt(now)
            .setNotifyUrl(ro.getNotifyUrl())
            .setCreatedIp(clientIp);
        paymentTradeService.validInsert(entity);
        return tradeSn;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoPayVo goPay(GoPayRo ro) {
        LocalDateTime now = LocalDateTime.now();
        final String clientIp = SpringContext.getClientIP().orElse(null);

        final String tradeSn = ro.getTradeSn();
        final String wxOpenId = ro.getWxOpenId();
        final PaymentWay paymentWay = CodeEnum.of(ro.getPaymentWay(), PaymentWay.class)
            .orElseThrow(() -> ServiceException.wrap("暂不支持该交易方式"));
        final PaymentTrade trade = paymentTradeService.selectOneByTradeSn(tradeSn)
            .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
        final Long tradeId = trade.getId();

        if (TradeStatus.Init.eq(trade.getTradeStatus())) {
            PaymentTrade update = new PaymentTrade().setTradeStatus(TradeStatus.Wait.getCode()).setUpdatedAt(now).setUpdatedIp(clientIp);
            boolean isOk = paymentTradeService.updateOneByIdAndTradeStatus(update, tradeId, TradeStatus.Init.getCode());
            ValidUtils.isTrue(isOk, "系统繁忙");
        } else if (TradeStatus.Wait.eq(trade.getTradeStatus())) {
            log.info("不进行处理");
        } else {
            throw ServiceException.wrap("系统繁忙");
        }

        PaymentPlatform paymentPlatform = paymentPlatformService.selectOneByTradeIdAndPaymentWayAndStatus(tradeId, paymentWay, Enabled.On)
            .orElseThrow(() -> ServiceException.wrap("未找到匹配的支付平台"));
        final Long paymentPlatformId = paymentPlatform.getId();

        // 新增支付记录
        paymentRecordService.validInsert(new PaymentRecord()
            .setPaymentPlatformId(paymentPlatformId)
            .setPaymentWay(paymentWay.getCode())
            .setTradeId(tradeId)
            .setWxOpenid(wxOpenId)
            .setCreatedAt(now)
            .setCreatedIp(clientIp));

        final GoPaymentResponse result = GoPaymentRequest.builder()
            .platformAppid(paymentPlatform.getPlatformAppid())
            .tradeSn(trade.getTradeSn())
            .wxOpenId(wxOpenId)
            .payAmount(trade.getTotalFee())
            .paymentWay(paymentWay)
            .build().request();
        return paymentMapping.asGoPayVo(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoRefundVo goRefund(GoRefundRo ro) {
        LocalDateTime now = LocalDateTime.now();
        final String clientIp = SpringContext.getClientIP().orElse(null);

        final String tradeSn = ro.getTradeSn();
        final PaymentTrade trade = paymentTradeService.selectOneByTradeSn(tradeSn)
            .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
        if (!TradeStatus.Success.eq(trade.getTradeStatus())) {
            ValidUtils.fail("交易未完成");
        }
        final PaymentWay paymentWay = CodeEnum.of(trade.getPaymentWay(), PaymentWay.class)
            .orElseThrow(() -> ServiceException.wrap("暂不支持该交易方式"));
        final PaymentPlatform paymentPlatform = paymentPlatformService.getById(trade.getPaymentPlatformId());
        if (Objects.isNull(paymentPlatform)) {
            log.info("交易单:[{}]上的支付平台[id={}]未找到", trade.getTradeSn(), trade.getPaymentPlatformId());
            ValidUtils.fail("交易单支付平台未找到");
        }

        final String refundSn = snCode.getRefundSNCode();
        RefundRecord entity = refundRecordMapping.asRefundRecord(ro)
            .setRefundSn(refundSn)
            .setRefundStatus(RefundStatus.Wait.getCode())
            .setCreatedAt(now)
            .setCreatedIp(clientIp);

        final GoRefundResponse result = GoRefundRequest.builder()
            .platformAppid(paymentPlatform.getPlatformAppid())
            .paymentWay(paymentWay)
            .tradeSn(trade.getTradeSn())
            .tradeNo(trade.getTradeNo())
            .refundSn(entity.getRefundSn())
            .totalFee(trade.getTotalFee())
            .refundFee(entity.getRefundFee())
            .build().request();
        entity.setRefundTradeNo(result.getRefundTradeNo());
        refundRecordService.validInsert(entity);
        return new GoRefundVo().setRefundSn(entity.getRefundSn());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String handleMessage(MessageContext context, String data) {
        final LambdaMap<Object> args = new StringMap<>();
        args.put(MessageContext::getAppid, context.getAppid());
        args.put(MessageContext::getPlatformTyp, context.getPlatformTyp());
        args.put(MessageContext::getFeature, context.getFeature());
        args.put(MessageContext::getPaymentWay, context.getPaymentWay());
        return ((PaymentMessage.Result) messageResolve.handle(context.asMessageType(), data, args)).string();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleRefundMessage(RefundMessageRo ro) {
        LocalDateTime now = LocalDateTime.now();
        final String clientIp = SpringContext.getClientIP().orElse(null);

        final String refundSn = ro.getRefundSn();
        final RefundRecord refund = refundRecordService.selectOneByRefundSn(refundSn)
            .orElseThrow(() -> ServiceException.wrap("退款失败"));


        final RefundStatus refundStatus = ro.getRefundStatus();

        // 如果已经处理
        if (LangUtils.equals(refund.getRefundSn(), refundSn) && refundStatus.eq(refund.getRefundStatus())) {
            return;
        }

        final RefundRecord updated = new RefundRecord()
            .setRefundTradeNo(ro.getRefundTradeNo())
            .setRefundStatus(refundStatus.getCode())
            .setRefundAt(ro.getRefundAt())
            .setSettlementRefundFee(ro.getSettlementRefundFee())
            .setUpdateIp(clientIp)
            .setUpdatedAt(now);
        final Long refundId = refund.getId();
        boolean isOk = refundRecordService.updateOneByIdAndTradeStatus(updated, refundId, RefundStatus.Wait.getCode());
        ValidUtils.isTrue(isOk, "退款失败");

        // 发送异步通知
        final Long notifyAppId = notifyAppService.saveRefundNotify(refundId);
        ((PaymentService) AopContext.currentProxy()).sendAsyncNotifyApp(notifyAppId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePaymentMessage(PaymentMessageRo ro) {
        LocalDateTime now = LocalDateTime.now();
        final String clientIp = SpringContext.getClientIP().orElse(null);

        final String appid = ro.getAppid();
        final Integer platformType = ro.getPlatformType();
        PaymentPlatform platform = paymentPlatformService.selectOneByPlatformAppidAndPlatformType(appid, platformType)
            .orElseThrow(() -> ServiceException.wrap("未匹配到支付平台"));

        final String tradeSn = ro.getTradeSn();
        final PaymentTrade paymentTrade = paymentTradeService.selectOneByTradeSn(tradeSn)
            .orElseThrow(() -> ServiceException.wrap("交易失败"));
        ValidUtils.isTrue(paymentTrade.getTotalFee().compareTo(ro.getTotalFee()) == 0, "交易金额不符合");

        final TradeStatus tradeStatus = ro.getTradeStatus();
        final String tradeNo = ro.getTradeNo();

        // 如果已经处理
        if (LangUtils.equals(paymentTrade.getTradeNo(), tradeNo) && tradeStatus.eq(paymentTrade.getTradeStatus())) {
            return;
        }

        final PaymentTrade update = new PaymentTrade()
            .setBuyerPayFee(ro.getBuyerPayFee())
            .setPaymentWay(ro.getPaymentWay().getCode())
            .setTradeStatus(tradeStatus.getCode())
            .setFinishAt(now)
            .setPaymentPlatformId(platform.getId())
            .setTradeNo(tradeNo)
            .setPaymentAt(ro.getPaymentAt())
            .setUpdatedAt(now)
            .setUpdatedIp(clientIp);

        final Long tradeId = paymentTrade.getId();

        boolean isOk = paymentTradeService.updateOneByIdAndTradeStatus(update, tradeId, TradeStatus.Wait.getCode());
        ValidUtils.isTrue(isOk, "交易失败");

        // 发送异步通知
        final Long notifyAppId = notifyAppService.savePaymentNotify(tradeId);
        ((PaymentService) AopContext.currentProxy()).sendAsyncNotifyApp(notifyAppId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryAsyncVo<TradeStatusSync> queryTrade(String tradeSn) {
        final PaymentTrade trade = paymentTradeService.selectOneByTradeSn(tradeSn)
            .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
        final Long appId = trade.getAppId();
        final PaymentApp paymentApp = paymentAppService.getById(appId);
        ValidUtils.notNull(paymentApp, "未找到接入应用");
        final Long platformId = trade.getPaymentPlatformId();
        log.info("交易单:[{}]上的支付平台[id={}]", trade.getTradeSn(), platformId);

        Integer platformType = null;
        if (Objects.nonNull(platformId)) {
            platformType = LangUtils.callIfNotNull(platformId, paymentPlatformService::getById)
                .map(PaymentPlatform::getPlatformType).orElse(null);
        }

        return new QueryAsyncVo<TradeStatusSync>()
            .setPlatformType(platformType)
            .setData(new TradeStatusSync()
                .setOpenid(trade.getWxOpenid())
                .setOutTradeSn(trade.getOutTradeSn())
                .setTradeSn(trade.getTradeSn())
                .setTotalFee(trade.getTotalFee())
                .setTradeStatus(trade.getTradeStatus())
                .setCreatedAt(trade.getCreatedAt())
                .setExpireAt(trade.getExpireAt())
                .setPaymentWay(trade.getPaymentWay())
                .setPaymentAt(trade.getPaymentAt()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WaitPaymentTradeVo getWaitPaymentTrade(String tradeSn) {
        final PaymentTrade trade = paymentTradeService.selectOneByTradeSn(tradeSn)
            .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
        return new WaitPaymentTradeVo()
            .setTotalFee(trade.getTotalFee())
            .setExpireAt(trade.getExpireAt())
            .setCreatedAt(trade.getCreatedAt());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PaymentWayVo> queryPaymentWay(QueryPaymentWayRo ro) {
        ValidUtils.validThrow(ro);
        final Long appId = paymentAppService.selectOneByAppSn(ro.getAppSn())
            .orElseThrow(() -> ServiceException.wrap("未授权接入方")).getId();
        final String sceneCode = ro.getSceneCode();
        return paymentWayRuleService.queryPaymentWay(appId, sceneCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryAsyncVo<RefundStatusSync> queryRefund(String refundSn) {
        final RefundRecord refund = refundRecordService.selectOneByRefundSn(refundSn)
            .orElseThrow(() -> ServiceException.wrap("未找到退款单据"));

        final PaymentTrade trade = paymentTradeService.selectOneByTradeSn(refund.getTradeSn())
            .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
        final Long paymentPlatformId = trade.getPaymentPlatformId();
        final PaymentPlatform paymentPlatform = paymentPlatformService.getById(paymentPlatformId);
        if (Objects.isNull(paymentPlatform)) {
            log.info("交易单:[{}]上的支付平台[id={}]未找到", trade.getTradeSn(), paymentPlatformId);
            ValidUtils.fail("交易单支付平台未找到");
        }

        return new QueryAsyncVo<RefundStatusSync>()
            .setPlatformType(paymentPlatform.getPlatformType())
            .setData(new RefundStatusSync()
                .setOpenid(trade.getWxOpenid())
                .setOutTradeSn(trade.getOutTradeSn())
                .setTradeSn(trade.getTradeSn())
                .setTotalFee(trade.getTotalFee())
                .setRefundSn(refundSn)
                .setOutRefundSn(refund.getOutRefundSn())
                .setRefundStatus(refund.getRefundStatus())
                .setRefundFee(refund.getRefundFee())
                .setSettlementRefundFee(refund.getSettlementRefundFee())
                .setRefundAt(refund.getRefundAt()));
    }

    @Async
    @Override
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void sendAsyncNotifyApp(Long notifyAppId) {
        // 最大通知次数
        final int MAX_NOTIFY_COUNT = 25;
        // 回调成功的标记
        final String SUCCESS_FLAG = "SUCCESS";

        final LocalDateTime now = LocalDateTime.now();
        final NotifyApp notifyApp = notifyAppService.getById(notifyAppId);
        ValidUtils.notNull(notifyApp, "未找到对应的通知");

        final String requestSn = notifyApp.getRequestSn();
        final PaymentNotifyType notifyType = CodeEnum.of(notifyApp.getNotifyType(), PaymentNotifyType.class)
            .orElseThrow(() -> ServiceException.wrap("通知类型错误"));
        NotifyAppAsyncVo data;

        String notifyUrl;

        switch (notifyType) {
            case Trade: {
                final PaymentTrade trade = paymentTradeService.selectOneByTradeSn(requestSn)
                    .orElseThrow(() -> ServiceException.wrap("未找到交易单据"));
                notifyUrl = trade.getNotifyUrl();
                if (StrUtil.isBlank(notifyUrl)) {
                    log.info("交易单据:[{}]无需进行通知, 没有设置通知地址", requestSn);
                    return;
                }

                data = new NotifyAppAsyncVo<TradeStatusSync>();
                final QueryAsyncVo<TradeStatusSync> query = this.queryTrade(requestSn);
                data.setData(query.getData()).setPlatformType(query.getPlatformType());
                break;
            }
            case Refund: {
                final RefundRecord refund = refundRecordService.selectOneByRefundSn(requestSn)
                    .orElseThrow(() -> ServiceException.wrap("未找到退款单据"));
                notifyUrl = refund.getNotifyUrl();
                if (StrUtil.isBlank(notifyUrl)) {
                    log.info("退款单据:[{}]无需进行通知, 没有设置通知地址", requestSn);
                    return;
                }

                data = new NotifyAppAsyncVo<RefundStatusSync>();
                final QueryAsyncVo<RefundStatusSync> query = this.queryRefund(requestSn);
                data.setData(query.getData()).setPlatformType(query.getPlatformType());
                break;
            }
            default:
                throw new IllegalArgumentException("通知类型错误");
        }
        final Long notifyId = notifyApp.getId();

        data.setNotifyAt(now)
            .setNotifyId(notifyId)
            .setNotifyType(notifyType.getCode());

        LocalDateTime finishAt;
        final NotifyAppLog entity = new NotifyAppLog()
            .setNotifyAppId(notifyId)
            .setNotifyBody(JsonUtils.toJSONString(data))
            .setCreatedAt(now);
        try {
            final String result = new RestTemplate().postForObject(notifyUrl, data, String.class);
            if (SUCCESS_FLAG.equalsIgnoreCase(result)) {
                entity.setNotifyResult(PaymentNotifyResult.Success.getCode());
            } else {
                entity.setNotifyResult(PaymentNotifyResult.Fail.getCode());
            }
        } catch (TimeoutException e) {
            entity.setNotifyResult(PaymentNotifyResult.Timeout.getCode());
        } catch (Exception e) {
            entity.setNotifyResult(PaymentNotifyResult.Fail.getCode());
        } finally {
            finishAt = LocalDateTime.now();
            entity.setUpdatedAt(finishAt);
            notifyAppLogService.validInsert(entity);
        }
        final List<NotifyAppLog> notifyAppLogs = notifyAppLogService.selectListByNotifyAppId(notifyId);

        if (notifyAppLogs.size() >= MAX_NOTIFY_COUNT) {
            final NotifyApp update = new NotifyApp().setId(notifyId).setFinishAt(finishAt).setUpdatedAt(finishAt);
            notifyAppService.updateById(update);
        }

    }

}
