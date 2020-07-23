package in.hocg.eagle.basic.message.core.normal;

import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.basic.message.core.MessageFactory;
import in.hocg.eagle.modules.com.entity.PersistenceMessage;
import in.hocg.eagle.utils.string.JsonUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;

/**
 * Created by hocgin on 2020/7/20.
 * email: hocgin@gmail.com
 * - destination
 * - header
 * - payload
 *
 * @author hocgin
 */
public class NormalMessageFactory implements MessageFactory  {
    public static final NormalMessageFactory ME = new NormalMessageFactory();

    private NormalMessageFactory() {
    }

    public SendResult syncSend(PersistenceMessage message) {
        final String destination = message.getDestination();
        final String headersStr = message.getHeaders();
        final MessageBuilder<String> messageBuilder = MessageBuilder.withPayload(message.getPayload());
        if (Strings.isNotBlank(headersStr)) {
            messageBuilder.copyHeaders(JsonUtils.parseObject(headersStr, HashMap.class));
        }
        return syncSend(destination, messageBuilder.build());
    }

    public SendResult syncSend(String destination, Message<?> message) {
        return getMQTemplate().syncSend(destination, message);
    }

    public SendResult syncSend(String destination, Message<?> message, long timeout) {
        return getMQTemplate().syncSend(destination, message, timeout);
    }

    private RocketMQTemplate getMQTemplate() {
        return SpringContext.getBean(RocketMQTemplate.class);
    }
}
