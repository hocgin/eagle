package in.hocg.eagle.modules.bmw.api;

import in.hocg.eagle.modules.bmw.helper.payment.request.PaymentRequestResult;
import in.hocg.eagle.modules.bmw.pojo.qo.CreatePaymentTransactionQo;
import in.hocg.eagle.modules.bmw.pojo.qo.GoPayQo;

/**
 * Created by hocgin on 2020/5/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface PaymentApi {

    /**
     * 创建支付交易信息
     *
     * @param data
     * @return 交易流水号
     */
    String createPaymentTransaction(CreatePaymentTransactionQo data);

    /**
     * 关闭支付交易
     *
     * @param transactionSn
     */
    void closePaymentTransaction(String transactionSn);

    /**
     * 申请第三方支付
     *
     * @param data
     * @return
     */
    PaymentRequestResult goPay(GoPayQo data);
}
