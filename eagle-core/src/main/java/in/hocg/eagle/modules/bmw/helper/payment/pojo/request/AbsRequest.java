package in.hocg.eagle.modules.bmw.helper.payment.pojo.request;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.constant.datadict.PaymentPlatform;
import in.hocg.eagle.basic.env.Env;
import in.hocg.eagle.utils.web.RequestUtils;
import in.hocg.payment.PaymentService;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.response.AliPayHttpResponse;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.response.WxPayXmlResponse;

/**
 * Created by hocgin on 2020/6/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbsRequest {

    protected String getHost() {
        return Env.getConfigs().getHostname();
    }

    protected String getClientIp() {
        return SpringContext.getRequest().map(RequestUtils::getClientIP).orElse(null);
    }

    protected PaymentService getPayService(PaymentPlatform platform, String appid) {
        switch (platform) {
            case AliPay:
                return SpringContext.getBean(AliPayService.class);
            case WxPay:
                return SpringContext.getBean(WxPayService.class);
            default:
                throw new UnsupportedOperationException();
        }
    }

    protected boolean isSuccess(WxPayXmlResponse response) {
        return "SUCCESS".equalsIgnoreCase(response.getResultCode());
    }

    protected boolean isSuccess(AliPayHttpResponse response) {
        return "10000".equalsIgnoreCase(response.getCode());
    }

    protected void save(GoPaymentRequest request) {

    }
}
