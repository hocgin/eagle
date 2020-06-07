package in.hocg.eagle.modules.bmw.pojo.vo;

import in.hocg.eagle.modules.bmw.helper.payment.request.PaymentRequestResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class GoPayVo {
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
    private PaymentRequestResult.WxJSAPI wxJSApi;
    private String wxNative;
}
