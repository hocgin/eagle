package in.hocg.eagle.modules.bmw2.service;

import in.hocg.eagle.modules.bmw2.pojo.ro.*;
import in.hocg.eagle.modules.bmw2.pojo.vo.*;

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
     * @param feature
     * @param channel
     * @param appid
     * @param data
     * @return
     */
    String handleMessage(Integer feature, Integer channel, String appid, String data);

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
}
