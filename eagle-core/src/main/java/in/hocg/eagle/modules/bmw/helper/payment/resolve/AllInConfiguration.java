package in.hocg.eagle.modules.bmw.helper.payment.resolve;

import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.AllInMessageResolve;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.MessageType;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.payment.AliPayPaymentMessageRule;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.payment.WxPayPaymentMessageRule;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.refund.AliPayRefundMessageRule;
import in.hocg.eagle.modules.bmw.helper.payment.resolve.message.rule.refund.WxPayRefundMessageRule;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.wxpay.v2.WxPayService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by hocgin on 2019/12/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
public class AllInConfiguration {

    @Bean
    public AllInMessageResolve messageResolve(WxPayService wxPayService,
                                              AliPayService aliPayService) {
        final AllInMessageResolve dataResolve = new AllInMessageResolve();

        // 微信支付
        dataResolve.addRule(MessageType.WxPayWithRefund, new WxPayPaymentMessageRule(wxPayService));
        dataResolve.addRule(MessageType.WxPayWithPayment, new WxPayRefundMessageRule(wxPayService));

        // 支付宝支付
        dataResolve.addRule(MessageType.AliPayWithPayment, new AliPayPaymentMessageRule(aliPayService));
        dataResolve.addRule(MessageType.AliPayWithRefund, new AliPayRefundMessageRule(aliPayService));
        return dataResolve;
    }


}
