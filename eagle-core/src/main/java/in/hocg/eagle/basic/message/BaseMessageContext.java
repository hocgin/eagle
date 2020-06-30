package in.hocg.eagle.basic.message;

import in.hocg.eagle.basic.ext.web.SpringContext;

/**
 * Created by hocgin on 2020/6/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class BaseMessageContext {

    public static void publishEvent(Object event) {
        SpringContext.getApplicationContext().publishEvent(event);
    }
}
