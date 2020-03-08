package in.hocg.eagle.basic;

import in.hocg.eagle.modules.notify.message.event.SubscriptionEvent;
import org.springframework.context.ApplicationContext;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class MessageContext {
    public static ApplicationContext getApplicationContext() {
        return SpringContext.getApplicationContext();
    }

    public static void publish(SubscriptionEvent event) {
        getApplicationContext().publishEvent(event);
    }
}
