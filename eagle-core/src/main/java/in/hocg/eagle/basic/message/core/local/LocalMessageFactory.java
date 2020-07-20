package in.hocg.eagle.basic.message.core.local;

import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.basic.message.core.MessageFactory;
import in.hocg.eagle.basic.message.event.SubscriptionEvent;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class LocalMessageFactory implements MessageFactory {
    public static final LocalMessageFactory ME = new LocalMessageFactory();

    private LocalMessageFactory() {
    }

    public void publish(SubscriptionEvent event) {
        this.publishEvent(event);
    }

    private void publishEvent(Object event) {
        SpringContext.getApplicationContext().publishEvent(event);
    }
}
