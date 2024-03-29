package in.hocg.eagle.modules.bmw.service;

import in.hocg.eagle.modules.bmw.api.ro.CreateTradeRo;
import in.hocg.eagle.modules.bmw.api.ro.GoPayRo;
import in.hocg.eagle.modules.bmw.api.ro.QueryPaymentWayRo;
import in.hocg.eagle.modules.bmw.api.vo.PaymentWayVo;
import in.hocg.eagle.modules.bmw.api.vo.QueryAsyncVo;
import in.hocg.eagle.modules.bmw.api.vo.RefundStatusSync;
import in.hocg.eagle.modules.bmw.api.vo.TradeStatusSync;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.MessageContext;
import in.hocg.eagle.modules.bmw.pojo.ro.GoRefundRo;
import in.hocg.eagle.modules.bmw.pojo.ro.PaymentMessageRo;
import in.hocg.eagle.modules.bmw.pojo.ro.RefundMessageRo;
import in.hocg.eagle.modules.bmw.pojo.vo.GoPayVo;
import in.hocg.eagle.modules.bmw.pojo.vo.GoRefundVo;
import in.hocg.eagle.modules.bmw.pojo.vo.WaitPaymentTradeVo;

import java.util.List;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface PaymentService {

    /**
     * 关闭交易单
     *
     * @param tradeSn 交易单
     * @return
     */
    void closeTrade(String tradeSn);

    /**
     * 创建交易单
     *
     * @param ro
     * @return
     */
    String createTrade(CreateTradeRo ro);

    /**
     * 支付请求
     *
     * @param ro
     * @return
     */
    GoPayVo goPay(GoPayRo ro);

    /**
     * 退款请求
     *
     * @param ro
     * @return
     */
    GoRefundVo goRefund(GoRefundRo ro);

    /**
     * 查询交易单
     *
     * @param tradeSn 交易单
     * @return
     */
    QueryAsyncVo<TradeStatusSync> queryTrade(String tradeSn);

    /**
     * 查询退款单
     *
     * @param refundSn 退款单
     * @return
     */
    QueryAsyncVo<RefundStatusSync> queryRefund(String refundSn);

    /**
     * 处理回调通知
     *
     * @param context
     * @param data
     * @return
     */
    String handleMessage(MessageContext context, String data);

    /**
     * 处理退款回调信息
     *
     * @param ro
     */
    void handleRefundMessage(RefundMessageRo ro);

    /**
     * 处理交易回调信息
     *
     * @param ro
     */
    void handlePaymentMessage(PaymentMessageRo ro);

    /**
     * 异步通知接入方
     *
     * @param notifyAppId 通知编号
     */
    void sendAsyncNotifyApp(Long notifyAppId);

    /**
     * 查询等待支付单据
     *
     * @param tradeSn
     * @return
     */
    WaitPaymentTradeVo getWaitPaymentTrade(String tradeSn);

    /**
     * 查询支付渠道
     *
     * @param ro
     * @return
     */
    List<PaymentWayVo> queryPaymentWay(QueryPaymentWayRo ro);
}
