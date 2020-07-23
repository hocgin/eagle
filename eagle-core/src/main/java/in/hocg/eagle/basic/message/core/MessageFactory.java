package in.hocg.eagle.basic.message.core;

import in.hocg.eagle.utils.ValidUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Created by hocgin on 2020/7/20.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface MessageFactory {

    default org.springframework.messaging.Message convert(Message payload) {
        final Class<?> aClass = payload.getClass();
        String destination = aClass.getSimpleName();
        final in.hocg.eagle.basic.message.annotation.MessageDestination destinationAnnotation = aClass.getAnnotation(in.hocg.eagle.basic.message.annotation.MessageDestination.class);
        if (Objects.nonNull(destinationAnnotation)) {
            String destinationVal = destinationAnnotation.value();
            if (Strings.isNotBlank(destinationVal)) {
                destination = destinationVal;
            }
        }

        Map<String, Object> headers = Collections.emptyMap();
        if (Message.class.isAssignableFrom(aClass)) {
            destination = payload.getDestination();
            headers = payload.getHeaders();
        }

        ValidUtils.notNull(destination, "消息未设置 destination");

        final DefaultMessage message = new DefaultMessage();
        message.setDestination(destination);
        message.setPayload(payload);
        message.setHeaders(headers);
        return message;
    }
}
