package in.hocg.eagle.modules.bmw.helper.payment;

import in.hocg.eagle.modules.bmw.helper.payment.dto.PaymentWay;
import in.hocg.eagle.modules.bmw.helper.payment.request.PaymentRequestResult;
import in.hocg.eagle.modules.bmw.pojo.qo.CreatePaymentTransactionQo;
import in.hocg.eagle.modules.bmw.pojo.qo.GoPayQo;
import in.hocg.eagle.modules.bmw.pojo.qo.GoRefundQo;
import in.hocg.eagle.modules.bmw.pojo.vo.RefundInfo;
import in.hocg.eagle.modules.bmw.pojo.vo.TransactionInfo;

/**
 * Created by hocgin on 2020/5/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface PaymentService {

    /**
     * 获取支付方式列表
     */
    void getPaymentWayList();

    /**
     * 创建支付流水
     */
    String createPaymentTransaction(CreatePaymentTransactionQo data);

    /**
     * 关闭支付流水
     *
     * @param transactionSn
     * @return
     */
    void closePaymentTransaction(String transactionSn);

    /**
     * 去支付(支付渠道/交易号)
     * - 生成支付记录
     *
     * @param data
     */
    PaymentRequestResult goPay(GoPayQo data);

    /**
     * 发起退款
     */
    String refund(GoRefundQo data);

    /**
     * 处理支付回调
     * - 支付方式
     * - 交易流水号
     */
    void handlePaymentCallback(PaymentWay paymentWay, String transactionSn, String tradeNo, boolean isSuccess);

    /**
     * 处理退款回调
     *
     * @param paymentWay
     * @param refundSn
     * @param refundTradeNo
     */
    void handleRefundCallback(PaymentWay paymentWay, String refundSn, String refundTradeNo, boolean isSuccess);

    /**
     * 通知退款结果
     */
    void sendRefundAsyncNotify(Long refundId, boolean isSuccess);

    /**
     * 通知支付结果
     *
     * @param transactionId
     * @param isSuccess
     */
    void sendPaymentAsyncNotify(Long transactionId, boolean isSuccess);

    /**
     * 查询交易结果(交易单号)
     */
    TransactionInfo queryTransaction(String transactionSn);

    /**
     * 查询退款结果(退款单号)
     */
    RefundInfo queryRefund(String refundSn);

    /**
     * 处理消息
     *
     * @param channel
     * @param feature
     * @param data
     * @return
     */
    String handleMessage(Integer channel, Integer feature, String data);
}
