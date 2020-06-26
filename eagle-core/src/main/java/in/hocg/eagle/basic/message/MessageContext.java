package in.hocg.eagle.basic.message;

import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.modules.mms.message.event.SubscriptionEvent;
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
