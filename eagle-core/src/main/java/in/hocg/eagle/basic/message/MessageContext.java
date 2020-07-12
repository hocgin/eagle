package in.hocg.eagle.basic.message;

import in.hocg.eagle.basic.message.event.SubscriptionEvent;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class MessageContext extends BaseMessageContext {

    public static void publish(SubscriptionEvent event) {
        MessageContext.publishEvent(event);
    }
}
