package in.hocg.eagle.basic.message.core.normal;

import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.basic.message.core.MessageFactory;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;

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
