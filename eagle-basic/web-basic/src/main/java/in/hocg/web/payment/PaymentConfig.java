package in.hocg.web.payment;

import in.hocg.web.env.Env;
import in.hocg.web.env.EnvConfigs;
import in.hocg.payment.ConfigStorages;
import in.hocg.payment.PaymentServices;
import in.hocg.payment.alipay.v2.AliPayConfigStorage;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.sign.SignType;
import in.hocg.payment.wxpay.sign.WxSignType;
import in.hocg.payment.wxpay.v2.WxPayConfigStorage;
import in.hocg.payment.wxpay.v2.WxPayService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by hocgin on 2019/12/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
public class PaymentConfig {

    @Bean
    AliPayService aliPayService() {
        final EnvConfigs configs = Env.getConfigs();
        final AliPayConfigStorage configStorage = ConfigStorages.createConfigStorage(AliPayConfigStorage.class)
                .setAliPayPublicKey(configs.getAliPayPublicKey())
                .setPrivateKey(configs.getAliPayPrivateKey())
                .setAppId(configs.getAliPayAppId())
                .setSignType(SignType.RSA2)
                .setIsDev(true);
        return PaymentServices.createPaymentService(AliPayService.class, configStorage);
    }

    @Bean
    WxPayService wxPayService() {
        final EnvConfigs configs = Env.getConfigs();
        final WxPayConfigStorage configStorage = ConfigStorages.createConfigStorage(WxPayConfigStorage.class)
                .setAppId(configs.getWxAppId())
                .setKey(configs.getWxKey())
                .setMchId(configs.getWxMchId())
                .setCertFile(new File(configs.getWxCertFile()))
                .setSignType(WxSignType.HMAC_SHA256)
                .setIsDev(false);
        return PaymentServices.createPaymentService(WxPayService.class, configStorage);
    }

}
