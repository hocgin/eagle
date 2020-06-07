package in.hocg.eagle.modules.bmw2.helper.payment.resolve.message;

import lombok.Data;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class MessageContext {
    private String appid;
    private Integer channel;
    private Integer feature;
    private Integer paymentWay;
}
