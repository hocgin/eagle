package in.hocg.eagle.modules.bmw.helper.payment.resolve.message;

import in.hocg.eagle.basic.exception.ServiceException;
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
    private Integer platformTyp;
    private Integer feature;
    private Integer paymentWay;

    public MessageType asMessageType() {
        return MessageType.of(platformTyp, feature).orElseThrow(() -> ServiceException.wrap("回调处理失败"));
    }
}
