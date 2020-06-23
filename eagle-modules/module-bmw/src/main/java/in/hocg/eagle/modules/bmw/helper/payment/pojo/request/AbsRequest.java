package in.hocg.eagle.modules.bmw.helper.payment.pojo.request;

import in.hocg.payment.PaymentRequest;
import in.hocg.payment.PaymentService;
import in.hocg.payment.alipay.v2.AliPayService;
import in.hocg.payment.alipay.v2.response.AliPayHttpResponse;
import in.hocg.payment.wxpay.v2.WxPayService;
import in.hocg.payment.wxpay.v2.response.WxPayXmlResponse;
import in.hocg.web.SpringContext;
import in.hocg.web.constant.datadict.PaymentPlatform;
import in.hocg.web.constant.datadict.PaymentWay;
import in.hocg.web.env.Env;
import in.hocg.web.payment.FeatureType;
import in.hocg.web.utils.ValidUtils;
import in.hocg.web.utils.string.JsonUtils;
import in.hocg.web.utils.web.RequestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by hocgin on 2020/6/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public abstract class AbsRequest {

    /**
     * 支付平台
     *
     * @return
     */
    protected abstract PaymentPlatform getPaymentPlatform();

    /**
     * 支付平台唯一标识
     *
     * @return
     */
    protected abstract String getPlatformAppid();

    protected String getHost() {
        return Env.getConfigs().getHostname();
    }

    protected String getPaymentNotifyUrl(PaymentWay paymentWay) {
        final String platformAppid = this.getPlatformAppid();
        final String notifyUrl = paymentWay.getNotifyUrl(FeatureType.Payment, platformAppid);
        return String.format("%s/payment/%s", this.getHost(), notifyUrl);
    }

    protected String getRefundNotifyUrl(PaymentWay paymentWay) {
        final String platformAppid = this.getPlatformAppid();
        final String notifyUrl = paymentWay.getNotifyUrl(FeatureType.Refund, platformAppid);
        return String.format("%s/payment/%s", this.getHost(), notifyUrl);
    }

    protected String getClientIp() {
        return SpringContext.getRequest().map(RequestUtils::getClientIP).orElse(null);
    }

    protected PaymentService getPayService() {
        final PaymentPlatform paymentPlatform = this.getPaymentPlatform();
        final String appid = this.getPlatformAppid();
        ValidUtils.notNull(appid, "支付平台唯一标识错误");
        switch (paymentPlatform) {
            case AliPay:
                return SpringContext.getBean(AliPayService.class);
            case WxPay:
                return SpringContext.getBean(WxPayService.class);
            default:
                throw new UnsupportedOperationException();
        }
    }

    protected <T> T request(PaymentRequest request) {
        final PaymentService paymentService = this.getPayService();
        this.save(this);
        return (T) paymentService.request(request);
    }

    protected boolean isSuccess(WxPayXmlResponse response) {
        return "SUCCESS".equalsIgnoreCase(response.getResultCode());
    }

    protected boolean isSuccess(AliPayHttpResponse response) {
        return "10000".equalsIgnoreCase(response.getCode());
    }

    protected void save(AbsRequest request) {
        log.info("发起请求: {}", JsonUtils.toJSONString(request));
    }
}
