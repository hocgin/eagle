package in.hocg.eagle.modules.bmw.helper.payment.request;

import lombok.Data;
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
    private Long paymentWay;
    private String url;
    private String form;
    private String method;
}
