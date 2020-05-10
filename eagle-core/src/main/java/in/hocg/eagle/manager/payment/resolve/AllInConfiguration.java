package in.hocg.eagle.manager.payment.resolve;

import in.hocg.eagle.manager.payment.resolve.message.AllInMessageResolve;
import in.hocg.eagle.manager.payment.resolve.message.MessageType;
import in.hocg.eagle.manager.payment.resolve.message.rule.AliPayTradeStatusSync;
import in.hocg.eagle.manager.payment.resolve.message.rule.WxPayRefundMessageRule;
import in.hocg.eagle.manager.payment.resolve.message.rule.WxUnifiedOrderMessageRule;
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
        dataResolve.addRule(MessageType.WxPay_UnifiedOrder, new WxUnifiedOrderMessageRule(wxPayService));
        dataResolve.addRule(MessageType.WxPay_PayRefund, new WxPayRefundMessageRule(wxPayService));

        // 支付宝支付
        dataResolve.addRule(MessageType.AliPay_TradeStatusSync, new AliPayTradeStatusSync(aliPayService));
        return dataResolve;
    }


}
