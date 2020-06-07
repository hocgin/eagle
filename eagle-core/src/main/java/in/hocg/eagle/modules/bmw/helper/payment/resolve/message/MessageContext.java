package in.hocg.eagle.modules.bmw.helper.payment.resolve.message;

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
public class MessageContext {
    private String appid;
    private Integer channel;
    private Integer feature;
    private Integer paymentWay;

    public MessageType asMessageType() {
        return MessageType.of(channel, feature);
    }
}
