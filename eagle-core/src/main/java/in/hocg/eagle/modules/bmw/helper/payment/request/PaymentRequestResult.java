package in.hocg.eagle.modules.bmw.helper.payment.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/5/31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class PaymentRequestResult {
    private Integer platform;
    private Integer paymentWay;
    private String url;
    private String method;

    @ApiModelProperty("表单")
    private String form;
    @ApiModelProperty("APP")
    private String app;
    @ApiModelProperty("二维码")
    private String qrCode;
    @ApiModelProperty("微信 - JSAPI")
    private WxJSAPI wxJSApi;
    private String wxNative;


    @Data
    @RequiredArgsConstructor
    public static class WxJSAPI {
        private final String timestamp;
        private final String nonceStr;
        @JsonAlias("package")
        private final String packageName;
        private final String signType;
        private final String paySign;

        public static WxJSAPI NEW(String timestamp, String nonceStr, String packageName, String signType, String paySign) {
            return new WxJSAPI(timestamp, nonceStr, "prepay_id=" + packageName, signType, paySign);
        }
    }
}
