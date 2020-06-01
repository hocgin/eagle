package in.hocg.eagle.modules.bmw.api.impl;

import in.hocg.eagle.modules.bmw.api.PaymentApi;
import in.hocg.eagle.modules.bmw.helper.payment.PaymentService;
import in.hocg.eagle.modules.bmw.helper.payment.request.PaymentRequestResult;
import in.hocg.eagle.modules.bmw.pojo.qo.CreatePaymentTransactionQo;
import in.hocg.eagle.modules.bmw.pojo.qo.GoPayQo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hocgin on 2020/5/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentApiImpl implements PaymentApi {
    private final PaymentService paymentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createPaymentTransaction(CreatePaymentTransactionQo data) {
        return paymentService.createPaymentTransaction(data);
    }

    @Override
    public void closePaymentTransaction(String transactionSn) {
        paymentService.closePaymentTransaction(transactionSn);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentRequestResult goPay(GoPayQo data) {
        return paymentService.goPay(data);
    }

}
